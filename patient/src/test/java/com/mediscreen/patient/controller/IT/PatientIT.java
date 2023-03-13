package com.mediscreen.patient.controller.IT;


import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.service.PatientService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@TestPropertySource({"/applicationtest.properties"})
@Sql(scripts = "/testpatient.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class PatientIT {
    private Patient patient1;

    private Patient patient2;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PatientService  patientService;

    @BeforeEach
    public void setUp() throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date_naissance1 = df.parse("1950-08-10");
        Date date_naissance2 = df.parse("1990-09-09");
        patient1 = new Patient(1L, "prenom1", "nom1", date_naissance1, "M", "0120254256", "adresse1");
        patient2 = new Patient(2L, "prenom2", "nom2", date_naissance2, "M", "0123521524", "adresse2");
    }


    @Test
    public void displayPatientListIT() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/patient/list"))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    @Order(2)
    void addValidePatient() throws Exception {
        String dateNaissance = new SimpleDateFormat("yyyy-MM-dd").format(patient1.getDateNaissance());
        mockMvc.perform(post("/patient/validate")
                        .param("prenom", patient1.getPrenom())
                        .param("nom", patient1.getNom())
                        .param("dateNaissance", dateNaissance)
                        .param("genre", patient1.getGenre())
                        .param("numeroTelephone", patient1.getNumeroTelephone())
                        .param("adresse", patient1.getAdresse()))
                .andExpect(model().hasNoErrors())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patient/list"));

        Patient patientSaved = patientService.findById(1L);
        assertEquals(patientSaved.getPrenom(), "prenom1");
        patientService.delete(1L);
    }

    @Test
    void updatePatient() throws Exception {
        String dateNaissance = new SimpleDateFormat("yyyy-MM-dd").format(patient1.getDateNaissance());
        patientService.create(patient1);
        mockMvc.perform(post("/patient/update/1")
                        .param("prenom", patient1.getPrenom())
                        .param("nom", "nom2")
                        .param("dateNaissance", dateNaissance)
                        .param("genre", patient1.getGenre())
                        .param("numeroTelephone", patient1.getNumeroTelephone())
                        .param("adresse", patient1.getAdresse()))
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/patient/list"));

        Patient patientUpdate = patientService.findById(1L);
        assertEquals(patientUpdate.getNom(), "nom2");

        patientService.delete(1L);
    }

    @Test
    void deletePatient() throws Exception {
        patientService.create(patient1);

        mockMvc.perform(get("/patient/delete/1"))
                .andExpect(redirectedUrl("/patient/list"));
    }
}
