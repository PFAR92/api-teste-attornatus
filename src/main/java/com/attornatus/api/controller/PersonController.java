package com.attornatus.api.controller;

import com.attornatus.api.domain.model.Person;
import com.attornatus.api.domain.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "people")
public class PersonController {

    private PersonService personService;

    @GetMapping
    public List<Person> list(){
        return personService.list();
    }

    @GetMapping(value = "/{id}")
    public Person searchId(@PathVariable Long id){
        return personService.searchId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Person save(@RequestBody Person person){
        return personService.save(person);
    }

    @PutMapping(value = "/{id}")
    public Person update(@PathVariable Long id,@RequestBody Person person){
        person.setId(id);
        return personService.update(person);
    }
}
