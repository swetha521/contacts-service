package com.test.contact.repository;

import com.test.contact.exception.ResourceAlreadyExistException;
import com.test.contact.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
public class ContactRepositoryTest {

    private final Contact contact1 = Contact.builder()
            .id(1)
            .firstName("A3")
            .lastName("B3")
            .address("ABC3")
            .phoneNumbers(Arrays.asList("3234567890"))
            .build();
    private final Contact contact2 = Contact.builder()
            .id(2)
            .firstName("A2")
            .lastName("B2")
            .address("ABC2")
            .phoneNumbers(Arrays.asList("2234567890"))
            .build();
    private ContactRepository contactRepository;

    @BeforeEach
    void setUp() {
        contactRepository = new ContactRepository();
        contactRepository.save(contact1);
        contactRepository.save(contact2);
    }

    @Test
    void createContact_ShouldReturnCreatedContact() {
        Contact contact = Contact.builder()
                .id(3)
                .firstName("A5")
                .lastName("B5")
                .address("ABC5")
                .phoneNumbers(Arrays.asList("3234567890"))
                .build();

        Mono<Contact> savedContact = contactRepository.save(contact);

        // then
        StepVerifier.create(savedContact)
                .expectNext(contact)
                .verifyComplete();
    }

    @Test
    void createContactWithExistingId_ShouldReturnException() {
        Contact contact = Contact.builder()
                .id(1)
                .firstName("A5")
                .lastName("B5")
                .address("ABC3")
                .phoneNumbers(Arrays.asList("3234567890"))
                .build();

        Mono<Contact> savedContact = contactRepository.save(contact);

        // then
        StepVerifier.create(savedContact)
                .expectErrorMatches(ex -> ex instanceof ResourceAlreadyExistException && ("400 BAD_REQUEST \"Contact already exists with id '1'\"").equalsIgnoreCase(ex.getMessage()))
                .verify();
    }

    @Test
    void createContactWithDuplicateInformation_ShouldReturnException() {
        Contact contact = Contact.builder()
                .id(5)
                .firstName("A3")
                .lastName("B3")
                .address("ABC3")
                .phoneNumbers(Arrays.asList("3234567890"))
                .build();

        Mono<Contact> savedContact = contactRepository.save(contact);

        // then
        StepVerifier.create(savedContact)
                .expectErrorMatches(ex -> ex instanceof ResourceAlreadyExistException && ("400 BAD_REQUEST \"Duplicate contact cannot be created [Contact{id=5, firstName='A3', lastName='B3', phoneNumbers=[3234567890], address='ABC3'}]\"").equalsIgnoreCase(ex.getMessage()))
                .verify();
    }

    @Test
    void findById_ShouldReturnExistingContact() {

        Mono<Contact> actualContact = contactRepository.findById(2);
        // then
        StepVerifier.create(actualContact)
                .expectNext(contact2)
                .verifyComplete();
    }

    @Test
    void findById_ShouldReturnExceptionWhenContactDoesNotExist() {

        Mono<Contact> actualContact = contactRepository.findById(110);
        // then
        StepVerifier.create(actualContact)
                .expectErrorMatches(ex -> ex instanceof ResourceNotFoundException && ("404 NOT_FOUND \"Contact not found with id '110'\"").equalsIgnoreCase(ex.getMessage()))
                .verify();
    }

    @Test
    void findByIds_ShouldReturnExistingContact() {

        Flux<Contact> contactList = contactRepository.findByIds(Arrays.asList(1, 2));
        // then
        StepVerifier.create(contactList)
                .expectNext(contact1, contact2)
                .verifyComplete();
    }
}