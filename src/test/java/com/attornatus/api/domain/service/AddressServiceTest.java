package com.attornatus.api.domain.service;

import com.attornatus.api.domain.exceptions.CustomException;
import com.attornatus.api.domain.model.Address;
import com.attornatus.api.domain.model.Person;
import com.attornatus.api.domain.repository.AddressRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressServiceTest {

    @Autowired
    private AddressService addressService;

    @MockBean
    private AddressRepository addressRepository;

    @MockBean
    private PersonService personService;


    private final Person testPerson = new Person();

    @Test
    public void savePersonAddressTest(){
        List<Address> addressList = new ArrayList<>();
        testPerson.setAddressList(addressList);

        when(personService.searchId(1L)).thenReturn(testPerson);
        Long id = 1L;

        var address = new Address();

        when(addressRepository.existsByStreetAndNumberAndCityAndPerson(address.getStreet(), address
                .getNumber(), address.getStreet(), testPerson))
                .thenReturn(false);
        when(addressRepository.findByStreetAndNumberAndCityAndPerson(address.getStreet(), address
                .getNumber(), address.getStreet(), testPerson)).thenReturn(address);

        //teste se for o primeiro endereço a cadastrar é colocado como principal
        Address testAddressSave = addressService.savePersonAddress(id, address);
        verify(addressRepository).save(any(Address.class));

        assertEquals(address, testAddressSave);
        assertTrue(address.getIsPrincipal());

        //testa o comportamento se outro endereço principal é cadastrado
        Address testAdrress2 = new Address(); testAdrress2.setIsPrincipal(true);
        addressService.savePersonAddress(id, testAdrress2);

        Person person = personService.searchId(id);

        assertTrue(person.getAddressList().stream().anyMatch(Address::getIsPrincipal));
        assertTrue(person.getAddressList().stream().anyMatch(address1 -> !address1.getIsPrincipal()));



    }

    @Test
    public void listPersonAddressesTest(){
        testPerson.setId(1L);
        var testAddress1 = new Address(1L, "Rua Teste", "12345-678", "123", "Cidade Teste", false, testPerson);
        var testAddress2 = new Address(2L, "Rua Teste", "12345-678", "123", "Cidade Teste", true, testPerson);
        var testAddress3 = new Address(3L, "Rua Teste", "12345-678", "123", "Cidade Teste", false, testPerson);

        List<Address> testAddresses = new ArrayList<>();
        testAddresses.add(testAddress1);
        testAddresses.add(testAddress2);
        testAddresses.add(testAddress3);
        testPerson.setAddressList(testAddresses);


        when(personService.mainAddressFirst(testAddresses)).thenReturn(testAddresses);
        when(personService.searchId(testPerson.getId())).thenReturn(testPerson);
        List<Address> expectedList = addressService.listPersonAddresses(testPerson.getId());

        assertEquals(testAddresses, expectedList);


    }

    @Test(expected = CustomException.class)
    public void addressCanBeRegistered(){
        testPerson.setId(1L);
        var testAddress = new Address(2L, "Rua Teste", "12345-678", "123", "Cidade Teste", false, testPerson);

        when(addressRepository.existsByStreetAndNumberAndCityAndPerson(testAddress.getStreet(), testAddress
                .getNumber(), testAddress.getCity(), testPerson))
                .thenReturn(true);
        when(addressRepository.findByStreetAndNumberAndCityAndPerson(testAddress.getStreet(), testAddress
                .getNumber(), testAddress.getCity(), testPerson)).thenReturn(testAddress);
        addressService.addressCanBeRegistered(testPerson, testAddress);
    }
}
