package com.mediscreen.risk.controller;


import com.mediscreen.risk.model.DTO.PatientDiabetes;
import com.mediscreen.risk.service.RiskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(RiskController.class)
public class RiskControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private RiskService riskService;

    @InjectMocks
    private RiskController riskController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(riskController).build();
    }

    @Test
    public void testGetRiskById() throws Exception {
        int patientId = 1;
        PatientDiabetes patientDiabetes = new PatientDiabetes();
        patientDiabetes.setFirstName("John");
        patientDiabetes.setSurname("Doe");
        patientDiabetes.setAge(28);
        patientDiabetes.setDiabetesAssessments("In Danger");

        when(riskService.getPatientDiabetes(patientId)).thenReturn(patientDiabetes);

        mockMvc.perform(get("/assess/risk/{idPatient}", patientId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.surname").value("Doe"))
                .andExpect(jsonPath("$.age").value(28))
                .andExpect(jsonPath("$.diabetesAssessments").value("In Danger"));
    }
}

