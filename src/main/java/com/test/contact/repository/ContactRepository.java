package com.test.contact.repository;

import com.test.contact.exception.ResourceAlreadyExistException;
import com.test.contact.exception.ResourceNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
@Log4j2
public class ContactRepository implements IRepository {
    private final Map<Integer, Contact> contactsData;

    public ContactRepository() {
        this.contactsData = new ConcurrentHashMap<>();
    }

    public Mono<Contact> findById(Integer id) {
        if (!contactsData.containsKey(id)) {
            log.error("Contact not found with id '\" + id + \"'");
            return Mono.error(new ResourceNotFoundException("Contact not found with id '" + id + "'"));
        }
        return Mono.just(contactsData.get(id));
    }

    public Flux<Contact> findByIds(List<Integer> ids) {
        return Flux.fromIterable(ids.stream().map(id -> {
            if (!contactsData.containsKey(id)) {
                log.error("Contact not found with id '\" + id + \"'");
                throw new ResourceNotFoundException("Contact not found with id '" + id + "'");
            }
            return contactsData.get(id);
        }).collect(Collectors.toList())).onErrorMap(exception -> exception);
    }

    public Mono<Contact> save(Contact contact) {
        Integer id = contact.getId();
        if (contactsData.containsKey(id)) {
            return Mono.error(new ResourceAlreadyExistException("Contact already exists with id '" + id + "'"));
        }

        if(contactsData.containsValue(contact)) {
            return Mono.error(new ResourceAlreadyExistException("Duplicate contact cannot be created [" + contact + "]"));
        }
        contactsData.put(id, contact);
        return Mono.just(contact);
    }

}
