package com.test.contact.repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IRepository {
    Mono<Contact> findById(Integer id);

    Mono<Contact> save(Contact contact);

    Flux<Contact> findByIds(List<Integer> ids);
}
