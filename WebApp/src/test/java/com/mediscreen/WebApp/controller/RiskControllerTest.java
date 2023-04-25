package com.mediscreen.WebApp.controller;

import com.mediscreen.WebApp.model.PatientDiabetesBean;
import com.mediscreen.WebApp.proxy.MicroservicePatientRiskProxy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RiskController.class)
public class RiskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MicroservicePatientRiskProxy microservicePatientRiskProxy;

    @Test
    void getRisk() throws Exception {
        int patientId = 1;
        PatientDiabetesBean patientDiabetesBean = new PatientDiabetesBean("John", "Doe", 32, "None");

        when(microservicePatientRiskProxy.getRiskById(patientId)).thenReturn(patientDiabetesBean);

        mockMvc.perform(get("/assess/risk/{idPatient}", patientId))
                .andExpect(status().isOk())
                .andExpect(view().name("assess/risk"))
                .andExpect(model().attribute("patientDiabetesBean", patientDiabetesBean));
    }
}
