package com.attornatus.api.domain.repository;

import com.attornatus.api.domain.model.Address;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    boolean existsByStreetAndNumberAndCity(String street, String number, String city);

}