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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date_naissance1 = LocalDate.parse("1950-08-10", dtf);
        LocalDate date_naissance2 =  LocalDate.parse("1990-09-09",dtf);
        patient1 = new Patient(1, "prenom1", "nom1", date_naissance1, "M", "tel10000000", "adresse1");
        patient2 = new Patient(2, "prenom2", "nom2", date_naissance2, "M", "tel20000000", "adresse2");
    }


    @Test
    public void displayPatientListIT() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/patients/list"))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    void addValidePatient() throws Exception {
        mockMvc.perform(post("/patients/validate")
                        .param("firstName", patient1.getFirstName())
                        .param("surname", patient1.getSurname())
                        .param("dateOfBirthday", patient1.getDateOfBirthday().toString())
                        .param("gender", patient1.getGender())
                        .param("phoneNumber", patient1.getPhoneNumber())
                        .param("address", patient1.getAddress()))
                .andExpect(model().hasNoErrors())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patients/list"));

        Patient patientSaved = patientService.findById(1);
        assertEquals(patientSaved.getFirstName(), "prenom1");
        patientService.delete(1);
    }

    @Test
    void updatePatient() throws Exception {
        patientService.create(patient1);
        mockMvc.perform(put("/patients/update/1")
                        .param("firstName", patient1.getFirstName())
                        .param("surname", "nom2")
                        .param("dateOfBirthday", patient1.getDateOfBirthday().toString())
                        .param("gender", patient1.getGender())
                        .param("phoneNumber", patient1.getPhoneNumber())
                        .param("address", patient1.getAddress()))
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/patients/list"));

        Patient patientUpdate = patientService.findById(1);
        assertEquals(patientUpdate.getSurname(), "nom2");

        patientService.delete(1);
    }

    @Test
    void deletePatient() throws Exception {
        patientService.create(patient1);

        mockMvc.perform(get("/patients/delete/1"))
                .andExpect(redirectedUrl("/patients/list"));
    }
}
