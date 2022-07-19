package com.test.contact.controller;

import com.test.contact.repository.Contact;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Arrays;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class ContactControllerIntegrationTest {

    public static final String CONTACTS_BASE_URI = "/v1/contacts";
    @Autowired
    private WebTestClient client;

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void getContactById_ShouldReturn404_WhenContactDoesNotExistWithGivenId() {

        client.get()
                .uri(CONTACTS_BASE_URI + "/100")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void getContactById_ShouldReturn200_ContactWithGivenId() {

        ContactRequestDTO contactRequestDTO = ContactRequestDTO.builder()
                .firstName("swetha")
                .lastName("saikam")
                .phoneNumbers(Arrays.asList("44-1234567890"))
                .address("W1J 0DD")
                .build();

        client.post()
                .uri(CONTACTS_BASE_URI + "/")
                .accept(APPLICATION_JSON)
                .bodyValue(contactRequestDTO)
                .exchange()
                .expectBody(Contact.class)
                .isEqualTo(Contact.builder()
                        .id(1)
                        .firstName("swetha")
                        .lastName("saikam")
                        .phoneNumbers(Arrays.asList("44-1234567890"))
                        .address("W1J 0DD")
                        .build());

        client.get()
                .uri(CONTACTS_BASE_URI + "/1")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void createContact_ShouldReturn200_WhenNewContactIsCreated() {
        ContactRequestDTO contactRequestDTO = ContactRequestDTO.builder()
                .firstName("swetha")
                .lastName("saikam")
                .phoneNumbers(Arrays.asList("44-1234567890"))
                .address("W1J 0DD")
                .build();

        client.post()
                .uri(CONTACTS_BASE_URI + "/")
                .accept(APPLICATION_JSON)
                .bodyValue(contactRequestDTO)
                .exchange()
                .expectBody(Contact.class)
                .isEqualTo(Contact.builder()
                        .id(1)
                        .firstName("swetha")
                        .lastName("saikam")
                        .phoneNumbers(Arrays.asList("44-1234567890"))
                        .address("W1J 0DD")
                        .build());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void createContact_ShouldReturn400_WhenContactDoesNotHaveFirstName() {
        ContactRequestDTO contactRequestDTO = ContactRequestDTO.builder()
                .lastName("saikam")
                .phoneNumbers(Arrays.asList("44-1234567890"))
                .address("W1J 0DD")
                .build();

        client.post()
                .uri(CONTACTS_BASE_URI + "/")
                .accept(APPLICATION_JSON)
                .bodyValue(contactRequestDTO)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void getContactByIds_ShouldReturn200_ForAllValidContactIds() {
        createMultipleContacts();

        client.get()
                .uri(CONTACTS_BASE_URI + "/ids/1,3")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void getContactByIds_ShouldReturn404_WhenNonExistingContactIdGiven() {
        createMultipleContacts();

        client.get()
                .uri(CONTACTS_BASE_URI + "/ids/1,3,100")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    private void createMultipleContacts() {
        ContactRequestDTO contactRequestDTO1 = ContactRequestDTO.builder()
                .firstName("swetha")
                .lastName("saikam")
                .phoneNumbers(Arrays.asList("44-1234567890"))
                .address("W1J 0DD")
                .build();

        client.post()
                .uri(CONTACTS_BASE_URI + "/")
                .accept(APPLICATION_JSON)
                .bodyValue(contactRequestDTO1)
                .exchange()
                .expectStatus()
                .isOk();

        ContactRequestDTO contactRequestDTO2 = ContactRequestDTO.builder()
                .firstName("firstname")
                .lastName("lastname")
                .phoneNumbers(Arrays.asList("44-1134567890"))
                .address("DA6 5RR")
                .build();

        client.post()
                .uri(CONTACTS_BASE_URI + "/")
                .accept(APPLICATION_JSON)
                .bodyValue(contactRequestDTO2)
                .exchange()
                .expectStatus()
                .isOk();


        ContactRequestDTO contactRequestDTO3 = ContactRequestDTO.builder()
                .firstName("firstname1")
                .lastName("lastname1")
                .phoneNumbers(Arrays.asList("44-1134567890"))
                .address("DA6 5RR")
                .build();

        client.post()
                .uri(CONTACTS_BASE_URI + "/")
                .accept(APPLICATION_JSON)
                .bodyValue(contactRequestDTO3)
                .exchange()
                .expectStatus()
                .isOk();
    }

}