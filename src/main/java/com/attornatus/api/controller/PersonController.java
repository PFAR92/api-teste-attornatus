package com.attornatus.api.controller;

import com.attornatus.api.domain.model.Person;
import com.attornatus.api.domain.service.PersonService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "people")
public class PersonController {

    private PersonService personService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Person save(@Valid @RequestBody Person person){
        return personService.save(person);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Person update(@PathVariable Long id,@Valid @RequestBody Person person){
        person.setId(id);
        return personService.update(person);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Person searchId(@PathVariable Long id){
        return personService.searchId(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Person> list(){
        return personService.list();
    }
}
