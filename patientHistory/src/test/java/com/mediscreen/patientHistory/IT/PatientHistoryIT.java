package com.mediscreen.patientHistory.IT;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.patientHistory.model.PatientHistory;
import com.mediscreen.patientHistory.repository.PatientHistoryRepository;
import com.mediscreen.patientHistory.service.PatientHistoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PatientHistoryIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PatientHistoryRepository patientHistoryRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private PatientHistory patientHistory1;
    private PatientHistory patientHistory2;

    private PatientHistoryService patientHistoryService;

    @BeforeEach
    public void setup() {
        patientHistoryRepository.deleteAll();
        patientHistory1 = new PatientHistory(null, 1, "Note 1", LocalDate.now());
        patientHistory2 = new PatientHistory(null, 2, "Note 2", LocalDate.now());
        patientHistory1 = patientHistoryRepository.save(patientHistory1);
        patientHistory2 = patientHistoryRepository.save(patientHistory2);
    }

    @Test
    public void testGetAllNotesByPatientId() throws Exception {
        mockMvc.perform(get("/pathistory/list/{patId}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].note").value(patientHistory1.getNote()));
    }

    @Test
    public void testGeNotesById() throws Exception {
        mockMvc.perform(get("/pathistory/NoteById/{id}", patientHistory1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.note").value(patientHistory1.getNote()));
    }

    @Test
    public void testCreate() throws Exception {
        mockMvc.perform(post("/pathistory/add")
                        .param("patId", "1")
                        .param("note", "Test note"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.patId", is(1)))
                .andExpect(jsonPath("$.note", is("Test note")));
    }
}
