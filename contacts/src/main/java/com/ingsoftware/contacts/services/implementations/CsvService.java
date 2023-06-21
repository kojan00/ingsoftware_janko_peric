package com.ingsoftware.contacts.services.implementations;

import com.ingsoftware.contacts.models.dtos.ContactResponseDTO;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

@Service
public class CsvService {

  private static final String[] HEADERS = {
    "First name", "Last Name", "Address", "Phone Number", "Contact Type"
  };

  private static final CSVFormat FORMAT = CSVFormat.DEFAULT.withHeader(HEADERS);

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
}
