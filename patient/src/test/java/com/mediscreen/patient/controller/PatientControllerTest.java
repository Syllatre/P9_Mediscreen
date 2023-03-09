package com.mediscreen.patient.controller;


import com.mediscreen.patient.configTest.ConfigurationTest;
import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date_naissance1 = df.parse("1950-08-10");
        Date date_naissance2 = df.parse("1990-09-09");
        patient1 = new Patient(20L, "prenom1", "nom1", date_naissance1, "M", "tel1", "adresse1");
        patient2 = new Patient(21L, "prenom2", "nom2", date_naissance2, "M", "tel2", "adresse2");
    }

    @Test
    public void displayPatientList() throws Exception {
        when(patientService.findAll()).thenReturn(Arrays.asList(patient1, patient2));
        mockMvc.perform(get("/patient/list"))
                .andExpect(model().attributeExists("patient"))
                .andExpect(view().name("patient/list"))
                .andExpect(status().isOk());

        verify(patientService).findAll();
    }

    @Test
    public void displayPatientForm() throws Exception {
        mockMvc.perform(get("/patient/add"))
                .andExpect(model().attributeExists("patient"))
                .andExpect(view().name("patient/add"))
                .andExpect(status().isOk());
    }

    @Test
    void addValidePatient() throws Exception {
        when(patientService.create(patient1)).thenReturn(patient1);
        when(patientService.findAll()).thenReturn(Arrays.asList(patient2));
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

        verify(patientService).create(any(Patient.class));
    }

    @Test
    void addValidePatientWithError() throws Exception {
        when(patientService.create(patient1)).thenReturn(patient1);
        when(patientService.findAll()).thenReturn(Arrays.asList(patient2));
        String dateNaissance = new SimpleDateFormat("yyyy-MM-dd").format(patient1.getDateNaissance());
        mockMvc.perform(post("/patient/validate")
                        .param("prenom", "")
                        .param("nom", patient1.getNom())
                        .param("dateNaissance", dateNaissance)
                        .param("genre", patient1.getGenre())
                        .param("numeroTelephone", patient1.getNumeroTelephone())
                        .param("adresse", patient1.getAdresse()))
                .andExpect(model().hasErrors())
                .andExpect(view().name("patient/add"))
                .andReturn();

        verify(patientService, times(0)).create(any(Patient.class));
    }

    @Test
    public void showUpdateFormPatient() throws Exception {
        when(patientService.findById(1L)).thenReturn(patient1);

        mockMvc.perform(get("/patient/update/1"))
                .andExpect(model().attributeExists("patient"))
                .andExpect(view().name("patient/update"))
                .andExpect(status().isOk());

        verify(patientService).findById(1L);
    }

    @Test
    void updatePatient() throws Exception {
        when(patientService.updatePatient(1L, patient1)).thenReturn(true);
        String dateNaissance = new SimpleDateFormat("yyyy-MM-dd").format(patient1.getDateNaissance());
        mockMvc.perform(post("/patient/update/1")
                        .param("prenom", patient1.getPrenom())
                        .param("nom", patient1.getNom())
                        .param("dateNaissance", dateNaissance)
                        .param("genre", patient1.getGenre())
                        .param("numeroTelephone", patient1.getNumeroTelephone())
                        .param("adresse", patient1.getAdresse()))
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/patient/list"));

    }

    @Test
    void deleteBidList() throws Exception {
        mockMvc.perform(get("/patient/delete/1"))
                .andExpect(redirectedUrl("/patient/list"));

        verify(patientService).delete(1L);
    }
}
