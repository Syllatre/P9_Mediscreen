package com.mediscreen.patient.service;

import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.repository.PatientRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PatientServiceTest {

    @InjectMocks
    private PatientService patientService;
    @Mock
    private PatientRepository patientRepository;

    private Optional<Patient> patient;


    @BeforeEach
    private void init() throws ParseException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date_naissance = LocalDate.parse("1950-08-10", dtf);
        patient = Optional.of(new Patient(20L, "Arthur", "Bongo", date_naissance, "M", "0240125245", "10 avenue du jardin"));
    }

    @AfterAll
    private void deletedBidList() {
        patientRepository.delete(patient.get());
    }

    @Test
    void updateTest() {
        when(patientRepository.findById(anyLong())).thenReturn(patient);
        Patient updatePatient = patient.get();
        updatePatient.setFirstName("Bob");
        when(patientRepository.save(updatePatient)).thenReturn(updatePatient);

        patientService.updatePatient(1L, patient.get());
        assertEquals(updatePatient.getFirstName(), "Bob");
        assertTrue(patientService.updatePatient(1L, patient.get()));
    }

    @Test
    void updateFailTest() {
        when(patientRepository.findById(anyLong())).thenReturn(Optional.empty());
        Patient updatePatient = patient.get();
        updatePatient.setFirstName("Bob");

        patientService.updatePatient(1L, patient.get());
        assertFalse(patientService.updatePatient(1L, patient.get()));
    }

    @Test
    void createTest() {
        when(patientRepository.save(patient.get())).thenReturn(patient.get());
        patientService.create(patient.get());

        verify(patientRepository, times(1)).save(patient.get());
    }

    @Test
    void deleteTest() {
        Patient patient1 = patient.get();
        patient1.setIdPatient(1L);
        patientService.delete(patient1.getIdPatient());

        verify(patientRepository).deleteById(any());
    }

    @Test
    void findAll() {
        List<Patient> patients = new ArrayList<>();
        patients.add(patient.get());
        when(patientRepository.findAll()).thenReturn(patients);
        assertEquals(patientRepository.findAll().size(), 1);
    }

    @Test
    void finByIdTest() {
        when(patientRepository.findById(anyLong())).thenReturn(patient);
        Patient patient1 = patientService.findById(1L);
        assertEquals(patient1.getFirstName(), "Arthur");
    }
}
