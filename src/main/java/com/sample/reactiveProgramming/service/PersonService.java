package com.sample.reactiveProgramming.service;

import com.sample.reactiveProgramming.domin.Person;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public interface PersonService {
    Mono<Person> getPersonById(Integer id);

    Flux<Person> getAllPerson();
}
