package com.test.contact.controller;

import com.test.contact.repository.Contact;
import com.test.contact.service.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/contacts")
public class ContactController {
    private static final Logger log = LoggerFactory.getLogger(ContactController.class);
    private final ContactService contactService;

    public ContactController(@Autowired ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/{id}")
    public Mono<Contact> getContact(@PathVariable("id") Integer contactId) {
        log.debug("Processing request for fetching contact with id [" + contactId + "]");
        return contactService.getContactById(contactId);
    }

    @GetMapping("/ids/{ids}")
    public Flux<Contact> getContactList(@PathVariable("ids") List<Integer> contactIds) {
        log.debug("Processing request for fetching contact with ids [" + contactIds + "]");
        return contactService.getContactsByIds(contactIds);
    }

    @PostMapping("/")
    public Mono<Contact> createContact(@Valid @RequestBody ContactRequestDTO contactDTO) {
        log.debug("Processing request for creating contact " + contactDTO);
        return contactService.createContact(contactDTO);
    }

}
