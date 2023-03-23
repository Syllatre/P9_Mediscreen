package com.mediscreen.patientHistory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.patientHistory.controller.PatientHistoryController;
import com.mediscreen.patientHistory.model.PatientHistory;
import com.mediscreen.patientHistory.service.PatientHistoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PatientHistoryControllerTest {
    @InjectMocks
    private PatientHistoryController patientHistoryController;

    @Mock
    private PatientHistoryService patientHistoryService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(patientHistoryController).build();
    }

    @Test
    void getAllNotesByPatientId() throws Exception {
        List<PatientHistory> patientHistories = new ArrayList<>();
        PatientHistory note1 = new PatientHistory();
        note1.setId("note1");
        note1.setPatId(1);
        note1.setNote("Test note 1");

        PatientHistory note2 = new PatientHistory();
        note2.setId("note2");
        note2.setPatId(1);
        note2.setNote("Test note 2");

        patientHistories.add(note1);
        patientHistories.add(note2);

        when(patientHistoryService.getNotesByPatientId(1)).thenReturn(patientHistories);

        mockMvc.perform(get("/patHistory/list/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is("note1")))
                .andExpect(jsonPath("$[0].patId", is(1)))
                .andExpect(jsonPath("$[0].note", is("Test note 1")))
                .andExpect(jsonPath("$[1].id", is("note2")))
                .andExpect(jsonPath("$[1].patId", is(1)))
                .andExpect(jsonPath("$[1].note", is("Test note 2")));

        verify(patientHistoryService, times(1)).getNotesByPatientId(1);
    }

    @Test
    void geNotesById() throws Exception {
        PatientHistory note = new PatientHistory();
        note.setId("note1");
        note.setPatId(1);
        note.setNote("Test note 1");

        when(patientHistoryService.getNoteById("note1")).thenReturn(note);

        mockMvc.perform(get("/patHistory/NoteById/note1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("note1")))
                .andExpect(jsonPath("$.patId", is(1)))
                .andExpect(jsonPath("$.note", is("Test note 1")));

        verify(patientHistoryService, times(1)).getNoteById("note1");
    }

    @Test
    void create() throws Exception {
        Integer patId = 1;
        String note = "Test note";


        PatientHistory expectedPatientHistory = new PatientHistory();
        expectedPatientHistory.setId("testId");
        expectedPatientHistory.setPatId(patId);
        expectedPatientHistory.setNote(note);
        expectedPatientHistory.setDate(LocalDate.now());

        when(patientHistoryService.create(patId, note)).thenReturn(expectedPatientHistory);

        // Send a POST request to create a new patient note
        mockMvc.perform(post("/patHistory/add")
                        .param("patId", patId.toString())
                        .param("note", note))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(expectedPatientHistory.getId())))
                .andExpect(jsonPath("$.patId", is(expectedPatientHistory.getPatId())))
                .andExpect(jsonPath("$.note", is(expectedPatientHistory.getNote())));

        verify(patientHistoryService, times(1)).create(patId, note);
    }

    @Test
    void updatePatientHistory() throws Exception {
        PatientHistory patientHistory = new PatientHistory();
        patientHistory.setId("testId");
        patientHistory.setPatId(1);
        patientHistory.setNote("New Note");

        when(patientHistoryService.updateNote(any(PatientHistory.class))).thenReturn(patientHistory);

        mockMvc.perform(put("/patHistory/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(patientHistory)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("testId")))
                .andExpect(jsonPath("$.patId", is(1)))
                .andExpect(jsonPath("$.note", is("New Note")));

        verify(patientHistoryService, times(1)).updateNote(any(PatientHistory.class));
    }

    @Test
    void deletePatientNote() throws Exception {
        String id = "note1";

        mockMvc.perform(delete("/patHistory/delete/" + id))
                .andExpect(status().isNoContent());

        verify(patientHistoryService, times(1)).delete(id);
    }

}
