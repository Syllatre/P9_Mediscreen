package com.mediscreen.patient.controller;


import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(PatientController.class)
public class PatientControllerTest {

    private Patient patient1;

    private Patient patient2;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    @BeforeEach
    public void setUp() throws ParseException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date_naissance1 = LocalDate.parse("1950-08-10", dtf);
        LocalDate date_naissance2 =  LocalDate.parse("1990-09-09",dtf);
        patient1 = new Patient(1L, "prenom1", "nom1", date_naissance1, "M", "tel10000000", "adresse1");
        patient2 = new Patient(2L, "prenom2", "nom2", date_naissance2, "M", "tel20000000", "adresse2");
    }

    @Test
    public void displayPatientList() throws Exception {
        when(patientService.findAll()).thenReturn(Arrays.asList(patient1, patient2));
        mockMvc.perform(get("/patients/list"))
                .andExpect(model().attributeExists("patient"))
                .andExpect(view().name("patients/list"))
                .andExpect(status().isOk());

        verify(patientService).findAll();
    }

    @Test
    public void displayPatientForm() throws Exception {
        mockMvc.perform(get("/patients/add"))
                .andExpect(model().attributeExists("patient"))
                .andExpect(view().name("patients/add"))
                .andExpect(status().isOk());
    }

    @Test
    void addValidePatient() throws Exception {
        when(patientService.create(patient1)).thenReturn(patient1);
        when(patientService.findAll()).thenReturn(Arrays.asList(patient2));
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

        verify(patientService).create(any(Patient.class));
    }

    @Test
    void addValidePatientWithError() throws Exception {
        when(patientService.create(patient1)).thenReturn(patient1);
        when(patientService.findAll()).thenReturn(Arrays.asList(patient2));
        mockMvc.perform(post("/patients/validate")
                        .param("firstName", "")
                        .param("surname", patient1.getSurname())
                        .param("dateOfBirthday", patient1.getDateOfBirthday().toString())
                        .param("gender", patient1.getGender())
                        .param("phoneNumber", patient1.getPhoneNumber())
                        .param("address", patient1.getAddress()))
                .andExpect(model().hasErrors())
                .andExpect(view().name("patients/add"))
                .andReturn();

        verify(patientService, times(0)).create(any(Patient.class));
    }

    @Test
    public void showUpdateFormPatient() throws Exception {
        when(patientService.findById(1L)).thenReturn(patient1);

        mockMvc.perform(get("/patients/update/1"))
                .andExpect(model().attributeExists("patient"))
                .andExpect(view().name("patients/update"))
                .andExpect(status().isOk());

        verify(patientService).findById(1L);
    }

    @Test
    void updatePatient() throws Exception {
        when(patientService.updatePatient(1L, patient1)).thenReturn(true);
        mockMvc.perform(put("/patients/update/1")
                        .param("firstName", patient1.getFirstName())
                        .param("surname", patient1.getSurname())
                        .param("dateOfBirthday", patient1.getDateOfBirthday().toString())
                        .param("gender", patient1.getGender())
                        .param("phoneNumber", patient1.getPhoneNumber())
                        .param("address", patient1.getAddress()))
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/patients/list"));

    }

    @Test
    void deleteBidList() throws Exception {
        mockMvc.perform(delete("/patients/delete/1"))
                .andExpect(redirectedUrl("/patients/list"));

        verify(patientService).delete(1L);
    }
}
