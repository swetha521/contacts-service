package com.test.contact.service;

import com.test.contact.controller.ContactRequestDTO;
import com.test.contact.repository.Contact;
import com.test.contact.repository.ContactRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ContactService {

    private final ContactRepository contactRepository;

    private final AtomicInteger sequence = new AtomicInteger(0);

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public Mono<Contact> getContactById(Integer id) {
        return contactRepository.findById(id);
    }

    public Flux<Contact> getContactsByIds(List<Integer> ids) {
        return contactRepository.findByIds(ids);
    }

    public Mono<Contact> createContact(ContactRequestDTO contactDTO) {
        Integer id = sequence.incrementAndGet();
        Contact contact = Contact.builder()
                .id(id)
                .firstName(contactDTO.getFirstName())
                .lastName(contactDTO.getLastName())
                .address(contactDTO.getAddress())
                .phoneNumbers(contactDTO.getPhoneNumbers())
                .build();
        return contactRepository.save(contact);
    }
}
