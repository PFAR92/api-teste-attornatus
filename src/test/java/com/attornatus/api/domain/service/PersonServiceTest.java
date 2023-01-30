package com.attornatus.api.domain.service;

import com.attornatus.api.domain.exceptions.CustomException;
import com.attornatus.api.domain.model.Address;
import com.attornatus.api.domain.model.Person;
import com.attornatus.api.domain.repository.PersonRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
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

    List<Address> testAddresses = new ArrayList<>();

    @Before
    public void setUp(){
        var testAddress1 = new Address(1L, "Rua Teste", "12345-678", "123", "Cidade Teste", false, null);
        var testAddress2 = new Address(2L, "Rua Teste", "12345-678", "123", "Cidade Teste", true, null);
        var testAddress3 = new Address(3L, "Rua Teste", "12345-678", "123", "Cidade Teste", false, null);
        testAddresses.add(testAddress1);
        testAddresses.add(testAddress2);
        testAddresses.add(testAddress3);
    }

    @Test
    public void listTest(){

        var testPerson1 = new Person(1L,"Paulo", LocalDate.of(2000,1,1), testAddresses);
        var testPerson2 = new Person(2L, "Felipe", LocalDate.of(2000,1,1), testAddresses);

        List<Person> expectedPersons = Arrays.asList(testPerson1, testPerson2);
        when(personRepository.findAll()).thenReturn(expectedPersons);
        List<Person>actualPersons = personService.list();
        assertEquals(expectedPersons, actualPersons);
        assertTrue(expectedPersons.get(0).getAddressList().get(0).getIsPrincipal());
        assertFalse(expectedPersons.get(0).getAddressList().get(1).getIsPrincipal());
        assertFalse(expectedPersons.get(0).getAddressList().get(2).getIsPrincipal());
        assertTrue(expectedPersons.get(1).getAddressList().get(0).getIsPrincipal());
        assertFalse(expectedPersons.get(1).getAddressList().get(1).getIsPrincipal());
        assertFalse(expectedPersons.get(1).getAddressList().get(2).getIsPrincipal());
    }

    @Test
    public void searchIdTest(){
        Long id = 1L;
        var testPerson = new Person(id, "Paulo", LocalDate.of(2000,1,1), testAddresses);

        when(personRepository.existsById(id)).thenReturn(true);
        when(personRepository.findById(id)).thenReturn(Optional.of(testPerson));
        Person expectedPerson = personService.searchId(id);
        assertEquals(expectedPerson, testPerson);
        assertTrue(expectedPerson.getAddressList().get(0).getIsPrincipal());
        assertFalse(expectedPerson.getAddressList().get(1).getIsPrincipal());
        assertFalse(expectedPerson.getAddressList().get(2).getIsPrincipal());
    }

    @Test
    public void saveTest(){
        var testPerson = new Person(1L, "Paulo", LocalDate.of(2000,1,1), testAddresses);
        personService.save(testPerson);
        verify(personRepository).save(any(Person.class));
    }

    @Test
    public void updateTest(){
        var testPerson = new Person(1L, "Paulo", LocalDate.of(2000,1,1), testAddresses);
        when(personRepository.existsById(testPerson.getId())).thenReturn(true);
        personService.update(testPerson);
        verify(personRepository).save(testPerson);
    }


    @Test(expected = CustomException.class)
    public void existsPersonTest(){
        Long id = 1L;
        when(personRepository.existsById(id)).thenReturn(false);
        personService.existsPerson(id);
    }

    @Test
    public void mainAddressFirstTest(){

        List<Address> addresses = personService.mainAddressFirst(testAddresses);
        assertTrue(addresses.get(0).getIsPrincipal());
        assertFalse(addresses.get(1).getIsPrincipal());
        assertFalse(addresses.get(2).getIsPrincipal());
    }



}
