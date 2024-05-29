package com.sample.reactiveProgramming.service;

import com.sample.reactiveProgramming.domin.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class PersonServiceImpl implements PersonService{

    Person ramu = new Person(10, "Ramu", "kumar");
    Person ragu = new Person(11, "Ragu", "kumar");
    Person sonu = new Person(12, "Sonu", "kumaran");
    Person abi = new Person(10, "abi", "saha");
    @Override
    public Mono<Person> getPersonById(Integer id) {
        return Mono.just(ragu);
    }

    @Override
    public Flux<Person> getAllPerson() {
        return Flux.just(ragu,sonu,abi,ramu);
    }
}
