package com.attornatus.api.controller;

import com.attornatus.api.domain.model.Address;
import com.attornatus.api.domain.model.Person;
import com.attornatus.api.domain.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PersonController.class)
public class PersonControllerTest {

    @MockBean
    private PersonService personService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final List<Address> testAddress = new ArrayList<>();

    @Test
    public void listTest() throws Exception {
        var testPerson1 = new Person(1L, "Paulo", testAddress);
        var testPerson2 = new Person(2L, "Felipe", testAddress);

        List<Person> expectedPersons = Arrays.asList(testPerson1, testPerson2);
        when(personService.list()).thenReturn(expectedPersons);

        String expectedJson = objectMapper.writeValueAsString(expectedPersons);
        mockMvc.perform(get("/people"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    public void searchId() throws Exception {
        Long id = 1L;
        var testPerson1 = new Person(id, "Paulo", testAddress);

        when(personService.searchId(id)).thenReturn(testPerson1);

        String expectedJson = objectMapper.writeValueAsString(testPerson1);
        mockMvc.perform(get("/people/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    public void saveTest() throws Exception {
        var testPerson = new Person(1L, "Paulo", testAddress);

        String expectedJson = objectMapper.writeValueAsString(testPerson);
        mockMvc.perform(post("/people")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expectedJson))
                        .andExpect(status().isCreated());
    }

    @Test
    public void updateTest() throws Exception {
        Long id = 1L;
        var testPerson = new Person(id, "Paulo", testAddress);

        String expectedJson = objectMapper.writeValueAsString(testPerson);
        mockMvc.perform(put("/people/{id}",id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expectedJson))
                        .andExpect(status().isOk());
    }



}
