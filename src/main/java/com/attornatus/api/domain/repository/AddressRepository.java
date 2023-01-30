package com.attornatus.api.domain.repository;

import com.attornatus.api.domain.model.Address;
import com.attornatus.api.domain.model.Person;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    Address findByStreetAndNumberAndCityAndPerson(String street, String number, String city, Person person);

    boolean existsByStreetAndNumberAndCityAndPerson(String street, String number, String city, Person person);
}