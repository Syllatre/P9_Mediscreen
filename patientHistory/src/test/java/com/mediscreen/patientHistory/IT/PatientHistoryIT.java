package com.mediscreen.patientHistory.IT;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.patientHistory.model.PatientHistory;
import com.mediscreen.patientHistory.repository.PatientHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PatientHistoryIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PatientHistoryRepository patientHistoryRepository;

    @BeforeEach
    void init() {
        patientHistoryRepository.deleteAll();
    }

    @Test
    void testAddPatientNote() throws Exception {
        PatientHistory patientHistory = new PatientHistory(null, 1, "Test note", LocalDate.now());

        mockMvc.perform(post("/patHistory/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("patId", "1")
                        .param("note", "Test note"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.patId").value(1))
                .andExpect(jsonPath("$.note").value("Test note"));
    }

    @Test
    void testGetAllNotesByPatientId() throws Exception {
        PatientHistory patientHistory1 = new PatientHistory(null, 1, "Note 1", LocalDate.now());
        PatientHistory patientHistory2 = new PatientHistory(null, 1, "Note 2", LocalDate.now());
        patientHistoryRepository.saveAll(List.of(patientHistory1, patientHistory2));

        mockMvc.perform(get("/patHistory/list/{patId}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].note").value("Note 1"))
                .andExpect(jsonPath("$[1].note").value("Note 2"));
    }

    @Test
    void testGetNoteById() throws Exception {
        PatientHistory patientHistory = new PatientHistory(null, 1, "Test note", LocalDate.now());
        PatientHistory savedPatientHistory = patientHistoryRepository.save(patientHistory);

        mockMvc.perform(get("/patHistory/NoteById/{id}", savedPatientHistory.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedPatientHistory.getId()))
                .andExpect(jsonPath("$.note").value("Test note"));
    }

    @Test
    void testUpdatePatientHistory() throws Exception {
        PatientHistory patientHistory = new PatientHistory(null, 1, "Test note", LocalDate.now());
        PatientHistory savedPatientHistory = patientHistoryRepository.save(patientHistory);

        PatientHistory updatedPatientHistory = new PatientHistory(savedPatientHistory.getId(), 1, "Updated note", LocalDate.now());

        mockMvc.perform(put("/patHistory/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedPatientHistory)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedPatientHistory.getId()))
                .andExpect(jsonPath("$.note").value("Updated note"));
    }

    @Test
    void testDeletePatientNote() throws Exception {
        PatientHistory patientHistory = new PatientHistory(null, 1, "Test note", LocalDate.now());
        PatientHistory savedPatientHistory = patientHistoryRepository.save(patientHistory);

        mockMvc.perform(delete("/patHistory/delete/{id}", savedPatientHistory.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/patHistory/NoteById/{id}", savedPatientHistory.getId()))
                .andExpect(status().isNotFound());
    }
}
