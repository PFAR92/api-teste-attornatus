package com.attornatus.api.domain.service;

import com.attornatus.api.domain.exceptions.CustomException;
import com.attornatus.api.domain.model.Address;
import com.attornatus.api.domain.model.Person;
import com.attornatus.api.domain.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class PersonService {

    private PersonRepository personRepository;
    public List<Person> list() {
        List<Person> people = new ArrayList<>();
        for (Person person : personRepository.findAll()){
            List<Address> listMainFirst = mainAddressFirst(person.getAddressList());
            person.setAddressList(listMainFirst);
            people.add(person);
        }
        return people;
    }

    public Person searchId(Long id){
        existsPerson(id);
        Person person = personRepository.findById(id).get();
        person.setAddressList(mainAddressFirst(person.getAddressList()));
        return person;

    }

    public Person save (Person person){
        return personRepository.save(person);
    }

    public Person update(Person person){
        existsPerson(person.getId());
        return personRepository.save(person);
    }

    public void delete(Long id){
        existsPerson(id);
        personRepository.deleteById(id);
    }


    public void existsPerson(Long id){
        if (!personRepository.existsById(id)){
            throw new CustomException("Essa pessoa não existe");
        }
    }

    //retorna sempre com o endereço principal em primeiro na lista
    public List<Address> mainAddressFirst(List<Address> list){
        return list.stream().sorted(Comparator.comparing(Address::getIsPrincipal).reversed()).toList();
    }
}
