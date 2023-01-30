package com.attornatus.api.controller;

import com.attornatus.api.domain.model.Address;
import com.attornatus.api.domain.service.AddressService;
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
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AddressController.class)
public class AddressControllerTest {

    @MockBean
    private AddressService addressService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void savePersonAddressTest() throws Exception {

        Long testPersonID = 1L;
        var testAddress = new Address(1L, "Rua Teste", "12345-678", "123", "Cidade Teste", false, null);


        when(addressService.savePersonAddress(testPersonID, testAddress)).thenReturn(testAddress);

        String expectedJson = objectMapper.writeValueAsString(testAddress);
        mockMvc.perform(post("/addresses/{id}", testPersonID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expectedJson))
                .andExpect(status().isCreated());

    }

    @Test
    public void listPersonAddressesTest() throws Exception {
        Long testPersonID = 1L;
        var testAddress1 = new Address();
        testAddress1.setStreet("Rua Teste");
        var testAddress2 = new Address();
        testAddress2.setStreet("Rua Teste2");
        List<Address> testListAddress = new ArrayList<>();
        testListAddress.add(testAddress1);
        testListAddress.add(testAddress2);

        when(addressService.listPersonAddresses(testPersonID)).thenReturn(testListAddress);

        String expectedJson = objectMapper.writeValueAsString(testListAddress);
        mockMvc.perform(get("/addresses/{id}",testPersonID))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

}
