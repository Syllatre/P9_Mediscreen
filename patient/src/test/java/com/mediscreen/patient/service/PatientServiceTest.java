package com.mediscreen.patient.service;

import com.mediscreen.patient.exception.NotFoundException;
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
import java.util.Arrays;
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

    private Patient patient1;
    private Patient patient2;


    @BeforeEach
    void setUp() {
        patient1 = new Patient(1L, "John", "Doe", LocalDate.of(1990, 1, 1), "M", "1234567890", "123 Main St");
        patient2 = new Patient(2L, "Jane", "Doe", LocalDate.of(1992, 1, 1), "F", "2345678901", "456 Main St");
    }

    @Test
    void create() {
        when(patientRepository.save(any(Patient.class))).thenReturn(patient1);
        Patient createdPatient = patientService.create(patient1);
        assertNotNull(createdPatient);
        assertEquals(patient1, createdPatient);
    }

    @Test
    void delete() {
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient1));
        patientService.delete(1L);
        verify(patientRepository, times(1)).deleteById(1L);
    }

    @Test
    void delete_NotFound() {
        when(patientRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> patientService.delete(1L));
    }

    @Test
    void findById() {
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient1));
        Patient foundPatient = patientService.findById(1L);
        assertNotNull(foundPatient);
        assertEquals(patient1, foundPatient);
    }

    @Test
    void findById_NotFound() {
        when(patientRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> patientService.findById(1L));
    }

    @Test
    void findAll() {
        when(patientRepository.findAll()).thenReturn(Arrays.asList(patient1, patient2));
        List<Patient> patients = patientService.findAll();
        assertNotNull(patients);
        assertEquals(2, patients.size());
    }

    @Test
    void updatePatient() {
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient1));

        Patient updatedPatient = new Patient(1L, "John", "Smith", LocalDate.of(1990, 1, 1), "M", "1234567890", "123 Main St");
        when(patientRepository.save(any(Patient.class))).thenReturn(updatedPatient);

        Patient result = patientService.updatePatient(updatedPatient);
        assertNotNull(result);
        assertEquals("Smith", result.getSurname());
    }
}
