package controller.validation;

import com.attornatus.api.controller.PersonController;
import com.attornatus.api.domain.model.Person;
import com.attornatus.api.domain.service.PersonService;
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

import java.time.LocalDate;
import java.util.ArrayList;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PersonController.class)
public class PersonControllerBeanValidationTest {

    @MockBean
    private PersonService personService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;



    private Person testPerson = new Person();

    @Before
    public void setUp(){
        testPerson.setId(1L);
        testPerson.setName("Paulo");
        testPerson.setBirthDate(LocalDate.of(2000,01,01));
        testPerson.setAddressList(new ArrayList<>());
    }

    @Test
    public void nameNotBlankSaveAndUpdateValidationTest() throws Exception {

        testPerson.setName("");
        String json = objectMapper.writeValueAsString(testPerson);
        mockMvc.perform(MockMvcRequestBuilders.post("/people")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());

        mockMvc.perform(MockMvcRequestBuilders.put("/people/{id}",testPerson.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void nameSizeSaveAndUpdateValidationTest() throws Exception {

        testPerson.setName("testetestetestetestetestetestetestetestetestetestetesteteste1");
        String json = objectMapper.writeValueAsString(testPerson);
        mockMvc.perform(MockMvcRequestBuilders.post("/people")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());

        mockMvc.perform(MockMvcRequestBuilders.put("/people/{id}", testPerson.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void birthDateNotNullSaveAndUpdateValidationTest() throws Exception {

        testPerson.setBirthDate(null);
        String json = objectMapper.writeValueAsString(testPerson);
        mockMvc.perform(MockMvcRequestBuilders.post("/people")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());

        mockMvc.perform(MockMvcRequestBuilders.put("/people/{id}", testPerson.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }
}
