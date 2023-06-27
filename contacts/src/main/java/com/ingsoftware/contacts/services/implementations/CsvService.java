package com.ingsoftware.contacts.services.implementations;

import com.ingsoftware.contacts.models.dtos.ContactResponseDTO;
import com.ingsoftware.contacts.models.entities.Contact;
import com.ingsoftware.contacts.models.entities.ContactType;
import com.ingsoftware.contacts.models.entities.User;
import com.ingsoftware.contacts.repositories.ContactRepository;
import com.ingsoftware.contacts.repositories.ContactTypeRepository;
import com.ingsoftware.contacts.repositories.UserRepository;
import io.hypersistence.tsid.TSID;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Arrays;
import java.util.List;

@Service
public class CsvService {

  private final ContactTypeRepository contactTypeRepository;

  private final ContactRepository contactRepository;

  private final UserRepository userRepository;

  private static final String[] HEADERS = {
    "First name", "Last name", "Address", "Phone number", "Contact type"
  };

  private static final CSVFormat FORMAT = CSVFormat.DEFAULT.withHeader(HEADERS);

  public CsvService(
      ContactTypeRepository contactTypeRepository,
      ContactRepository contactRepository,
      UserRepository userRepository) {
    this.contactTypeRepository = contactTypeRepository;
    this.contactRepository = contactRepository;
    this.userRepository = userRepository;
  }

  public ByteArrayInputStream load(final List<ContactResponseDTO> contactResponseDTOList) {
    return writeDataToCsv(contactResponseDTOList);
  }

  private ByteArrayInputStream writeDataToCsv(final List<ContactResponseDTO> contactResponseDTOS) {
    try (final ByteArrayOutputStream stream = new ByteArrayOutputStream();
        final CSVPrinter printer = new CSVPrinter(new PrintWriter(stream), FORMAT)) {

      for (final ContactResponseDTO contactResponseDTO : contactResponseDTOS) {
        List<String> data =
            Arrays.asList(
                contactResponseDTO.firstName(),
                contactResponseDTO.lastName(),
                contactResponseDTO.address(),
                contactResponseDTO.phoneNumber(),
                contactResponseDTO.contactType().type());
        printer.printRecord(data);
      }
      printer.flush();
      return new ByteArrayInputStream(stream.toByteArray());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public String importContacts(MultipartFile multipartFile, long tsidUser) {

    int savedContacts = 0;
    int failedContacts = 0;

    try {
      BufferedReader fileReader =
          new BufferedReader(new InputStreamReader(multipartFile.getInputStream(), "UTF-8"));

      CSVFormat csvFormat =
          CSVFormat.DEFAULT.builder().setHeader(HEADERS).setSkipHeaderRecord(true).build();
      CSVParser csvParser = new CSVParser(fileReader, csvFormat);

      Iterable<CSVRecord> records = csvParser.getRecords();


      outerloop:
      for (CSVRecord record : records) {
        if (!record.isConsistent()) {
          System.out.println("inconsistent");
          failedContacts++;
          continue;
        }
        for (String value : record) {
          if (value.isBlank()) {
            System.out.println("blank");
            failedContacts++;
            continue outerloop;
          }
        }
        String firstName = record.get("First name");
        String lastName = record.get("Last name");
        String address = record.get("Address");
        String phoneNumber = record.get("Phone number");

        // Check if there is an existing contact with same phone number
        Contact existingContact = contactRepository.findByPhoneNumber(phoneNumber);
        if (existingContact != null) {
          failedContacts++;
          continue;
        }

        ContactType contactType = contactTypeRepository.findByType(record.get("Contact type"));
        long tsid = TSID.fast().toLong();
        User user = userRepository.findByTsid(tsidUser);


        Contact contact = new Contact();

        contact.setFirstName(firstName);
        contact.setLastName(lastName);
        contact.setPhoneNumber(phoneNumber);
        contact.setAddress(address);
        contact.setTsid(tsid);
        contact.setContactType(contactType);
        contact.setUser(user);

        contactRepository.save(contact);
        savedContacts++;
      }
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return "Succesfully imported " + savedContacts + " contacts. Failed to import " + failedContacts + " contacts.";
  }
}
