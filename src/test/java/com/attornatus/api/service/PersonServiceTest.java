package com.attornatus.api.service;

import com.attornatus.api.domain.model.Address;
import com.attornatus.api.domain.model.Person;
import com.attornatus.api.domain.repository.PersonRepository;
import com.attornatus.api.domain.service.PersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonServiceTest {

    @Autowired
    private PersonService personService;

    @MockBean
    private PersonRepository personRepository;

    List<Address> testAddress = new ArrayList<>();

    @Test
    public void listTest(){
        var testPerson1 = new Person(1L,"Paulo",testAddress);
        var testPerson2 = new Person(2L, "Felipe",testAddress);

        List<Person> expectedPersons = Arrays.asList(testPerson1, testPerson2);
        when(personRepository.findAll()).thenReturn(expectedPersons);
        List<Person>actualPersons = personService.list();
        assertEquals(expectedPersons, actualPersons);
    }

    @Test
    public void searchIdTest(){
        Long id = 1L;
        var testPerson = new Person(id, "Paulo", testAddress);

        when(personRepository.findById(id)).thenReturn(Optional.of(testPerson));
        Person expectedPerson = personService.searchId(id);
        assertEquals(expectedPerson, testPerson);
    }

    @Test
    public void saveTest(){
        var testPerson = new Person(1L, "Paulo", testAddress);
        personService.save(testPerson);
        verify(personRepository).save(any(Person.class));
    }

    @Test
    public void updateTest(){
        var testPerson = new Person(1L, "Paulo", testAddress);
        when(personRepository.existsById(testPerson.getId())).thenReturn(true);
        personService.update(testPerson);
        verify(personRepository).save(testPerson);
    }

    @Test
    public void deleteTest(){
        var testPerson = new Person(1L, "Paulo", testAddress);
        when(personRepository.existsById(testPerson.getId())).thenReturn(true);
        personService.delete(testPerson.getId());
        verify(personRepository).deleteById(testPerson.getId());
    }
}
