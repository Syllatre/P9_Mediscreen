package com.mediscreen.patient.controller.IT;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.repository.PatientRepository;
import com.mediscreen.patient.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@TestPropertySource({"/applicationtest.properties"})
@Sql(scripts = "/testpatient.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class PatientIT {
    private Patient patient1;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PatientService patientService;
    @Autowired
    private PatientRepository patientRepository;

    @BeforeEach
    public void setUp() throws ParseException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date_naissance1 = LocalDate.parse("1950-08-10", dtf);
        patient1 = new Patient(1, "prenom1", "nom1", date_naissance1, "M", "tel10000000", "adresse1");
    }

    public static String asJsonString(final Object object) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void displayPatientListIT() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/patients/list"))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    void addValidePatient() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/patients/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(patient1)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void updatePatient() throws Exception {
        Patient initialPatient = new Patient(null, "John", "Doe", LocalDate.of(1990, 1, 1), "M", "1234567890", "123 Main St");
        initialPatient = patientService.create(initialPatient);

        Patient updatedPatient = new Patient(initialPatient.getIdPatient(), "Johnny", "Doe", LocalDate.of(1990, 1, 1), "M", "1234567890", "123 Main St");

        mockMvc.perform(put("/patients/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedPatient)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.surname").value(updatedPatient.getSurname()));

        Patient updatedPatientInDb = patientService.findById(initialPatient.getIdPatient());
        assertThat(updatedPatientInDb != null);
        assertThat(updatedPatientInDb.getSurname()).isEqualTo(updatedPatient.getSurname());
    }

    @Test
    public void getPatientById() throws Exception {
        Patient patient = new Patient(null, "John", "Doe", LocalDate.of(1990, 1, 1), "M", "1234567890", "123 Main St");
        patient = patientService.create(patient);


        mockMvc.perform(get("/patients/{idPatient}", patient.getIdPatient()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.surname").value(patient.getSurname()));
    }

    @Test
    public void deletePatient() throws Exception {

        Patient patient = new Patient(null, "John", "Doe", LocalDate.of(1990, 1, 1), "M", "1234567890", "123 Main St");
        patient = patientService.create(patient);

        mockMvc.perform(delete("/patients/delete/{idPatient}", patient.getIdPatient()))
                .andDo(print())
                .andExpect(status().isNoContent());

        Optional<Patient> deletedPatient = patientRepository.findById(patient.getIdPatient());
        assertThat(deletedPatient).isNotPresent();
    }
}
