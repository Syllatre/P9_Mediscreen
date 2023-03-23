package com.mediscreen.patient.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.service.PatientService;
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
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PatientController.class)
public class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PatientService patientService;

    private List<Patient> patientList;

    @BeforeEach
    public void setUp() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date_naissance1 = LocalDate.parse("1990-01-01", dtf);
        LocalDate date_naissance2 = LocalDate.parse("1992-02-02", dtf);
        patientList = Arrays.asList(
                new Patient(1L, "John", "Doe", date_naissance1, "M", "1234567890", "123 Main St"),
                new Patient(2L, "Jane", "Doe", date_naissance2, "F", "0987654321", "456 Main St")
        );
    }

    @Test
    public void getAllPatient() throws Exception {
        when(patientService.findAll()).thenReturn(patientList);

        mockMvc.perform(get("/patients/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(patientList.size()));

        verify(patientService, times(1)).findAll();
    }

    @Test
    public void addPatient() throws Exception {
        Patient newPatient = new Patient( "Alice", "Smith", LocalDate.of(1995, 5, 5), "F", "1112223333", "789 Main St");
        Patient savedPatient = new Patient(3l, "Alice", "Smith", LocalDate.of(1995, 5, 5), "F", "1112223333", "789 Main St");

        when(patientService.create(any(Patient.class))).thenReturn(savedPatient);

        mockMvc.perform(post("/patients/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newPatient)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value(savedPatient.getFirstName()))
                .andExpect(jsonPath("$.surname").value(savedPatient.getSurname()))
                .andExpect(jsonPath("$.dateOfBirthday", is(savedPatient.getDateOfBirthday().toString())))
                .andExpect(jsonPath("$.gender").value(savedPatient.getGender()))
                .andExpect(jsonPath("$.phoneNumber").value(savedPatient.getPhoneNumber()))
                .andExpect(jsonPath("$.address").value(savedPatient.getAddress()));

        verify(patientService, times(1)).create(any(Patient.class));
    }

    @Test
    public void updatePatient() throws Exception {
        Patient updatedPatient = new Patient(1L, "Johnny", "Doe", LocalDate.of(1990, 1, 1), "M", "1234567890", "123 Main St");

        when(patientService.updatePatient(updatedPatient)).thenReturn(updatedPatient);

        mockMvc.perform(put("/patients/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedPatient)))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(mvcResult -> System.out.println("Response JSON: " + mvcResult.getResponse().getContentAsString())) // Ajouter cette ligne pour imprimer la réponse JSON
                .andExpect(jsonPath("$.surname").value(updatedPatient.getFirstName()));

        verify(patientService, times(1)).updatePatient(updatedPatient);

    }

    @Test
    void deletePatientNote() throws Exception {
        int id = 1;
        Long idL = 1L;

        mockMvc.perform(delete("/patients/delete/1"))
                .andExpect(status().isNoContent());

        verify(patientService, times(1)).delete(idL);
    }
}
