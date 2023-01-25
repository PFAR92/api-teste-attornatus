package com.attornatus.api.domain.service;

import com.attornatus.api.domain.exceptions.NotFoundException;
import com.attornatus.api.domain.model.Person;
import com.attornatus.api.domain.repository.AddressRepository;
import com.attornatus.api.domain.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PersonService {

    private PersonRepository personRepository;

    private AddressRepository addressRepository;


    public List<Person> list() {
        return personRepository.findAll();
    }

    public Person searchId(Long id){
        return personRepository.findById(id).orElseThrow(()->new NotFoundException("essa pessoa não existe"));
    }

    public Person save (Person person){
        return personRepository.save(person);
    }

    public Person update(Person person){
        if (existsPerson(person.getId())) personRepository.save(person);
        return person;
    }

    public void delete(Long id){
        if (existsPerson(id)) personRepository.deleteById(id);
    }

    private boolean existsPerson(Long id){
        if (personRepository.existsById(id)){
           return true;
        } else {
            throw new NotFoundException("Essa pessoa não existe");
        }
    }
}
