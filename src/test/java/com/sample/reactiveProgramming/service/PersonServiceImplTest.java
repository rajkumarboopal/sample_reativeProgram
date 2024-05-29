package com.sample.reactiveProgramming.service;

import com.sample.reactiveProgramming.domin.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

class PersonServiceImplTest {

    PersonServiceImpl personService;

    @BeforeEach
    void setUp(){
        personService = new PersonServiceImpl();
    }

    @Test
    public  void getByIdBlock(){
        Mono<Person> personMono = personService.getPersonById(10);

        Person person = personMono.block();

        System.out.println("Person "+ person);
    }

    @Test
    public void getByIdSubscribe(){
        Mono<Person> personMono = personService.getPersonById(10);

        StepVerifier.create(personMono).expectNextCount(1).verifyComplete();

        personMono.subscribe(person -> {
            System.out.println(person.toString());
        });
    }

    @Test
    public void getIdMapFunction(){
        Mono<Person> personMono = personService.getPersonById(11);
        personMono.map(person -> {
            System.out.println("Person "+ person);
            return person.getFistName();
        }).subscribe(firstname->{
            System.out.println("Person firstname "+ firstname);
        });
    }

    @Test
    public void getFluxFirstBlock(){
        Flux<Person> personFlux = personService.getAllPerson();
        Person person = personFlux.blockFirst();
        System.out.println("Person "+ person.toString());
    }

    @Test
    public void getFluxSubscribe(){
        Flux<Person> personFlux = personService.getAllPerson();

        StepVerifier.create(personFlux).expectNextCount(4).verifyComplete();
        personFlux.subscribe(person -> {
            System.out.println(person);
        });
    }

    @Test
    public void getFluxToListMono(){
        Flux<Person> personFlux = personService.getAllPerson();

        Mono<List<Person>> listMono = personFlux.collectList();
        listMono.subscribe(persons -> {
            persons.forEach(person -> {
                System.out.println("Person -->"+ person);
            });
        });
    }

    @Test
    public void findPersonById(){
        Flux<Person> personFlux = personService.getAllPerson();

        StepVerifier.create(personFlux).expectNextCount(1).verifyComplete();
        final Integer id = 12;
        Mono<Person> personMono = personFlux.filter(person -> person.getId() == id).next();

        personMono.subscribe(person -> {
           System.out.println(person.toString());
        });
    }

    @Test
    public void findPersonNotFound(){
        Flux<Person> personFlux = personService.getAllPerson();

        StepVerifier.create(personFlux).expectNextCount(0).verifyComplete();
        final Integer id = 15;
        Mono<Person> personMono = personFlux.filter(person -> person.getId() == id).next();

        personMono.subscribe(person -> {
            System.out.println(person.toString());
        });
    }
    @Test
    public void findPersonNotFoundWithException(){
        Flux<Person> personFlux = personService.getAllPerson();

        final Integer id = 15;
        Mono<Person> personMono = personFlux.filter(person -> person.getId() == id).single();

        personMono.doOnError(throwable -> {
            System.out.println("Its Boom....");
        }).onErrorReturn(Person.builder().id(id).build())
                .subscribe(person -> {
            System.out.println(person.toString());
        });
    }

}