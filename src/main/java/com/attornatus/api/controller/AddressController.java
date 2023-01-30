package com.attornatus.api.controller;

import com.attornatus.api.domain.model.Address;
import com.attornatus.api.domain.service.AddressService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "addresses")
public class AddressController {

    private AddressService addressService;

    @PostMapping(value = "/{personId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Address savePersonAddress(@PathVariable Long personId, @Valid @RequestBody Address address){
        return addressService.savePersonAddress(personId, address);
    }

    @GetMapping(value = "/{personId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Address> listPersonAddresses(@PathVariable Long personId){
        return addressService.listPersonAddresses(personId);
    }

}
