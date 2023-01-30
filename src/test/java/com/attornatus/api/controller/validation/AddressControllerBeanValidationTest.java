package com.attornatus.api.controller.validation;

import com.attornatus.api.controller.AddressController;
import com.attornatus.api.domain.model.Address;
import com.attornatus.api.domain.service.AddressService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AddressController.class)
public class AddressControllerBeanValidationTest {

    @MockBean
    private AddressService addressService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Address testAddress = new Address();
    @Before
    public void setUp(){
        testAddress.setId(1L);
        testAddress.setStreet("Rua Teste");
        testAddress.setCep("12345-678");
        testAddress.setNumber("123");
        testAddress.setCity("Cidade Teste");
        testAddress.setIsPrincipal(true);
    }

    private Long personId = 1L;

    @Test
    public void streetNotBlankAndSizeSaveValidationTest() throws Exception {
        testAddress.setStreet("");

        when(addressService.savePersonAddress(personId, testAddress)).thenReturn(testAddress);

        String jsonNotBlank = objectMapper.writeValueAsString(testAddress);
        mockMvc.perform(MockMvcRequestBuilders.post("/addresses/{id}", personId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonNotBlank))
                .andExpect(status().isBadRequest());

        testAddress.setStreet("testetestetestetestetestetestetestetestetestetestetesteteste1");
        String jsonSize61 = objectMapper.writeValueAsString(testAddress);
        mockMvc.perform(MockMvcRequestBuilders.post("/addresses/{id}", personId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonSize61))
                .andExpect(status().isBadRequest());


    }

    @Test
    public void cepNotBlankSaveValidationTest() throws Exception{
        testAddress.setCep("");
        when(addressService.savePersonAddress(personId, testAddress)).thenReturn(testAddress);
        String jsonNotBlank = objectMapper.writeValueAsString(testAddress);

        mockMvc.perform(MockMvcRequestBuilders.post("/addresses/{id}", personId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonNotBlank))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void numberNotBlankAndSizeSaveValidationTest() throws Exception {
        testAddress.setNumber("");

        when(addressService.savePersonAddress(personId, testAddress)).thenReturn(testAddress);

        String jsonNotBlank = objectMapper.writeValueAsString(testAddress);
        mockMvc.perform(MockMvcRequestBuilders.post("/addresses/{id}", personId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonNotBlank))
                .andExpect(status().isBadRequest());

        testAddress.setNumber("12345678910");
        String jsonSize11 = objectMapper.writeValueAsString(testAddress);
        mockMvc.perform(MockMvcRequestBuilders.post("/addresses/{id}", personId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonSize11))
                .andExpect(status().isBadRequest());


    }

    @Test
    public void cityNotBlankAndSizeSaveValidationTest() throws Exception {
        testAddress.setCity("");

        when(addressService.savePersonAddress(personId, testAddress)).thenReturn(testAddress);

        String jsonNotBlank = objectMapper.writeValueAsString(testAddress);
        mockMvc.perform(MockMvcRequestBuilders.post("/addresses/{id}", personId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonNotBlank))
                .andExpect(status().isBadRequest());

        testAddress.setCity("testetestetestetestetestetestetestetestetestetestetesteteste1");
        String jsonSize61 = objectMapper.writeValueAsString(testAddress);
        mockMvc.perform(MockMvcRequestBuilders.post("/addresses/{id}", personId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonSize61))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void isPrincipalNotNullSaveValidationTest() throws Exception{
        testAddress.setIsPrincipal(null);
        when(addressService.savePersonAddress(personId, testAddress)).thenReturn(testAddress);
        String jsonNotNull = objectMapper.writeValueAsString(testAddress);

        mockMvc.perform(MockMvcRequestBuilders.post("/addresses/{id}", personId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonNotNull))
                .andExpect(status().isBadRequest());
    }



}
