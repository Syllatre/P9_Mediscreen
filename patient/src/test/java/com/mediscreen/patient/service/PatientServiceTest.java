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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date_naissance = df.parse("1950-08-10");
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
        updatePatient.setPrenom("Bob");
        when(patientRepository.save(updatePatient)).thenReturn(updatePatient);

        patientService.updatePatient(1L, patient.get());
        assertEquals(updatePatient.getPrenom(), "Bob");
        assertTrue(patientService.updatePatient(1L, patient.get()));
    }

    @Test
    void updateFailTest() {
        when(patientRepository.findById(anyLong())).thenReturn(Optional.empty());
        Patient updatePatient = patient.get();
        updatePatient.setPrenom("Bob");

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
        assertEquals(patient1.getPrenom(), "Arthur");
    }
}
