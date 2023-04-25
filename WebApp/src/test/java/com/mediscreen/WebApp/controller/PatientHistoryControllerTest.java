package com.mediscreen.WebApp.controller;

import com.mediscreen.WebApp.model.PatientBean;
import com.mediscreen.WebApp.model.PatientHistoryBean;
import com.mediscreen.WebApp.proxy.MicroservicePatientHistoryProxy;
import com.mediscreen.WebApp.proxy.MicroservicePatientProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PatientHistoryController.class)
public class PatientHistoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MicroservicePatientHistoryProxy patientHistoryProxy;

    @MockBean
    private MicroservicePatientProxy patientProxy;

    private PatientBean patient;
    private PatientHistoryBean patientHistory;

    @BeforeEach
    public void setUp() {
        patient = new PatientBean(1, "John", "Doe", LocalDate.of(1990, 1, 1), "M", "1234567890", "123 Main St");
        patientHistory = new PatientHistoryBean("1", 1, "Note", LocalDate.now());
    }

    @Test
    public void getAllNotesByPatientId_shouldReturnNotesList() throws Exception {
        when(patientProxy.getPatientById(1)).thenReturn(patient);
        when(patientHistoryProxy.getAllNotesByPatientId(1)).thenReturn(Arrays.asList(patientHistory));

        mockMvc.perform(get("/pathistory/list/{idPatient}", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("pathistory/list"))
                .andExpect(model().attributeExists("patHistory"));

        verify(patientProxy, times(1)).getPatientById(1);
        verify(patientHistoryProxy, times(1)).getAllNotesByPatientId(1);
    }

    @Test
    public void addNoteForm_shouldReturnAddForm() throws Exception {
        when(patientProxy.getPatientById(1)).thenReturn(patient);

        mockMvc.perform(get("/pathistory/add/{idPatient}", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("pathistory/add"))
                .andExpect(model().attributeExists("patientHistory"));

        verify(patientProxy, times(1)).getPatientById(1);
    }

    @Test
    public void validate_shouldAddNoteAndRedirect() throws Exception {
        when(patientProxy.getPatientById(1)).thenReturn(patient);
        when(patientHistoryProxy.create(1, "Test note")).thenReturn(patientHistory);

        mockMvc.perform(post("/pathistory/add/{idPatient}", 1)
                        .param("noteText", "Test note"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/pathistory/list/" + patient.getIdPatient()));

        verify(patientHistoryProxy, times(1)).create(1, "Test note");
    }

    @Test
    public void deletePatientHistory_shouldDeleteAndRedirect() throws Exception {
        mockMvc.perform(get("/pathistory/delete/{idPatient}/{id}", 1, "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/pathistory/list/1"));

        verify(patientHistoryProxy, times(1)).deletePatientNote("1");
    }

    @Test
    public void getNoteById_shouldReturnUpdateForm() throws Exception {
        when(patientProxy.getPatientById(1)).thenReturn(patient);
        when(patientHistoryProxy.geNotesById("1")).thenReturn(patientHistory);

        mockMvc.perform(get("/pathistory/update/{idPatient}/{id}", 1, "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("pathistory/update"))
                .andExpect(model().attributeExists("patientHistory"));

        verify(patientProxy, times(1)).getPatientById(1);
        verify(patientHistoryProxy, times(1)).geNotesById("1");
    }

    @Test
    public void updateNote_shouldUpdateAndRedirect() throws Exception {
        PatientHistoryBean patientHistory = new PatientHistoryBean("1", 1, "Updated note", LocalDate.now());


        when(patientProxy.getPatientById(1)).thenReturn(patient);
        when(patientHistoryProxy.updatePatientHistory(patientHistory)).thenReturn(patientHistory);

        mockMvc.perform(post("/pathistory/update/{idPatient}/{id}", 1, "1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("noteText", "Updated note"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/pathistory/list/1"));

//        verify(patientProxy, times(1)).getPatientById(1);
//        verify(patientHistoryProxy, times(1)).updatePatientHistory(patientHistory);
    }


}

