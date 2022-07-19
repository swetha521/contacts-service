package com.test.contact.service;

import com.test.contact.controller.ContactRequestDTO;
import com.test.contact.repository.Contact;
import com.test.contact.repository.ContactRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ContactServiceTest {

    private ContactService contactService;
    @Mock
    private ContactRepository mockContactRepository;

    @BeforeEach
    void setUp() {
        contactService = new ContactService(mockContactRepository);
    }

    @Test
    void createContact_ShouldReturnCreatedContact() {
        // when
        Contact expectedContact = Contact.builder()
                .id(3)
                .firstName("A3")
                .lastName("B3")
                .address("ABC3")
                .phoneNumbers(Arrays.asList("3234567890"))
                .build();

        when(mockContactRepository.save(any(Contact.class))).thenReturn(Mono.just(expectedContact));

        ContactRequestDTO contactRequestDTO = ContactRequestDTO.builder()
                .firstName("A3")
                .lastName("B3")
                .address("ABC3")
                .phoneNumbers(Arrays.asList("3234567890"))
                .build();

        Mono<Contact> contact = contactService.createContact(contactRequestDTO);

        // then
        StepVerifier.create(contact)
                .expectNext(expectedContact)
                .verifyComplete();
    }


    @Test
    void getContactById_ShouldReturnContactIfPresent() {
        // when
        Contact contact1 = mock(Contact.class);

        when(mockContactRepository.findById(any(Integer.class))).thenReturn(Mono.just(contact1));

        Mono<Contact> contact = contactService.getContactById(1);

        StepVerifier.create(contact)
                .expectNext(contact1)
                .verifyComplete();
    }

    @Test
    void getContactByIds_ShouldReturnContactsIfPresent() {
        // when
        Contact contact1 = mock(Contact.class);
        Contact contact2 = mock(Contact.class);

        when(mockContactRepository.findByIds(any(List.class))).thenReturn(Flux.just(contact1, contact2));

        Flux<Contact> contacts = contactService.getContactsByIds(Arrays.asList(1, 2));

        StepVerifier.create(contacts)
                .expectNext(contact1, contact2)
                .verifyComplete();
    }
}