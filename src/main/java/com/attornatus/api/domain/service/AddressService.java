package com.attornatus.api.domain.service;

import com.attornatus.api.domain.exceptions.CustomException;
import com.attornatus.api.domain.model.Address;
import com.attornatus.api.domain.model.Person;
import com.attornatus.api.domain.repository.AddressRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class AddressService {

    private AddressRepository addressRepository;
    private PersonService personService;

    @Transactional
    public Address savePersonAddress(Long personId, Address address){
        Person person = personService.searchId(personId);
        List<Address> addressList = new ArrayList<>(person.getAddressList());

        if (addressRepository.existsByStreetAndNumberAndCity(address.getStreet(), address.getNumber(), address.getCity())){
            throw new CustomException("Esse endereço já está cadastrado");
        }

        //se não tiver nenhum endereço salvo, ele é colocado como principal
        if (addressList.isEmpty()){
            address.setIsPrincipal(true);
        } else if (address.getIsPrincipal()){
            for (Address addressPerson : addressList){
                if (addressPerson.getIsPrincipal()) addressPerson.setIsPrincipal(false);
            }
        }
        address.setPerson(person);
        addressRepository.save(address);
        addressList.add(address);
        person.setAddressList(addressList);
        personService.update(person);
        return address;
    }

    public List<Address> listPersonAddresses(Long personId){
        Person person = personService.searchId(personId);
        return personService.mainAddressFirst(person.getAddressList());
    }


}
