package com.ingsoftware.contacts.services.implementations;

import com.ingsoftware.contacts.exceptions.ContactNotFoundException;
import com.ingsoftware.contacts.models.dtos.ContactRequestDTO;
import com.ingsoftware.contacts.models.dtos.ContactResponseDTO;
import com.ingsoftware.contacts.models.dtos.ContactTypeDTO;
import com.ingsoftware.contacts.models.entities.Contact;
import com.ingsoftware.contacts.models.entities.ContactType;
import com.ingsoftware.contacts.models.entities.Role;
import com.ingsoftware.contacts.models.entities.User;
import com.ingsoftware.contacts.repositories.ContactRepository;
import com.ingsoftware.contacts.repositories.ContactTypeRepository;
import com.ingsoftware.contacts.repositories.UserRepository;
import com.ingsoftware.contacts.services.interfaces.ContactService;
import com.ingsoftware.contacts.services.mappers.ContactRequestMapper;
import com.ingsoftware.contacts.services.mappers.ContactResponseMapper;
import com.ingsoftware.contacts.services.mappers.ContactTypeMapper;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContactServiceImplementationTest {

  @Mock ContactRepository contactRepository;
  @Mock UserRepository userRepository;
  @Mock ContactRequestMapper contactRequestMapper;
  @Mock ContactResponseMapper contactResponseMapper;
  @Mock ContactTypeRepository contactTypeRepository;

  @Mock ContactTypeMapper contactTypeMapper;

  @InjectMocks ContactServiceImplementation contactService;

  private Contact givenContact;

  private List<Contact> givenContacts = new ArrayList<>();
  private List<ContactResponseDTO> expectedResult = new ArrayList<>();

  private ContactResponseDTO contactResponseDTO;

  private ContactRequestDTO contactRequestDTO;

  private ContactTypeDTO contactTypeDTO;

  @BeforeEach
  void setUp() {
    givenContact =
        new Contact(
            1111L,
            "janko",
            "peric",
            "lala",
            "21421",
            new ContactType(512512512L, "Work"),
            new User(333333L, "Paja", "Pavlovic", "paja@gmail.com", "sifra", Role.USER, "425-242"));

    contactTypeDTO = new ContactTypeDTO("Work");

    contactResponseDTO = new ContactResponseDTO("janko", "peric", "lala", "21421", contactTypeDTO);

    contactRequestDTO = new ContactRequestDTO("janko", "peric", "lala", "21421",  new ContactType(512512512L, "Work"));

    givenContacts.add(givenContact);
    expectedResult.add(contactResponseDTO);
  }

  @Test
  void willFindAllByUser() {
    // given
    given(contactRepository.findAllByUser(33333L)).willReturn(givenContacts);
    given(contactResponseMapper.toDtolist(givenContacts)).willReturn(expectedResult);

    // when
    List<ContactResponseDTO> result = contactService.findAllByUser(33333L);

    // then
    assertThat(result).isNotNull();
    assertThat(result.size()).isEqualTo(expectedResult.size());
  }

  @Test
  void willFindAllByFirstNameKeyword() {
    // given
    String keyword = "janko";
    given(contactRepository.findAllByFirstNameKeyword(33333L, keyword)).willReturn(givenContacts);
    given(contactResponseMapper.toDtolist(givenContacts)).willReturn(expectedResult);

    // when
    List<ContactResponseDTO> result = contactService.findAllByFirstNameKeyword(33333L, keyword);

    // then
    assertThat(result).isNotNull();
    assertThat(result.size()).isEqualTo(expectedResult.size());
  }

  /*@Test
  void willSaveContact() {
    // given
    given(contactRepository.findByPhoneNumber(contactRequestDTO.phoneNumber())).willReturn(null);
    given(contactRepository.save(givenContact)).willReturn(givenContact);
    given(contactResponseMapper.toDto(givenContact)).willReturn(contactResponseDTO);

    // when
    ContactResponseDTO result = contactService.save(contactRequestDTO);
  }
*/
  @Test
  void willDeleteByTsid() {
    // given
    long tsid = 1111L;
    String expected = "Contact successfully deleted.";
    given(contactRepository.findByTsid(tsid)).willReturn(givenContact);
    given(contactRepository.deleteByTsid(tsid)).willReturn("Contact successfully deleted.");

    // when
    String result = contactService.deleteByTsid(tsid);

    // then
    assertThat(result).isEqualTo(expected);
  }

  @Test
  void willNotDeleteByTsid() {
    // given
    long tsid = 1111L;

    // when
    Exception exception =
        assertThrows(
            Exception.class,
            () -> {
              contactService.deleteByTsid(1111L);
            });

    // then
    assertEquals("Contact not found.", exception.getMessage());
  }
}
