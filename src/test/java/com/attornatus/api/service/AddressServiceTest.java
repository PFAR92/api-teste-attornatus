package com.attornatus.api.service;

import com.attornatus.api.domain.model.Address;
import com.attornatus.api.domain.model.Person;
import com.attornatus.api.domain.repository.AddressRepository;
import com.attornatus.api.domain.service.AddressService;
import com.attornatus.api.domain.service.PersonService;
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

        Address address = new Address();
        address.setIsPrincipal(true);

        Address testAddress = addressService.savePersonAddress(1L, address);
        verify(addressRepository).save(any(Address.class));

        assertEquals(address, testAddress);
        assertEquals(1, addressList.size());
        assertTrue(addressList.get(0).getIsPrincipal());

    }

    @Test
    public void listPersonAddressesTest(){
        testPerson.setId(1L);
        var testAddress1 = new Address();
        testAddress1.setStreet("rua teste1");
        testAddress1.setPerson(testPerson);
        var testAddress2 = new Address();
        testAddress2.setStreet("Rua teste2");
        testAddress2.setPerson(testPerson);

        List<Address> testAddressList = new ArrayList<>();
        testAddressList.add(testAddress1);
        testAddressList.add(testAddress2);
        testPerson.setAddressList(testAddressList);


        when(personService.searchId(testPerson.getId())).thenReturn(testPerson);
        List<Address> expectedList = addressService.listPersonAddresses(testPerson.getId());

        assertEquals(testAddressList, expectedList);


    }
}
