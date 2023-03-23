package com.mediscreen.patientHistory.service;

import com.mediscreen.patientHistory.exception.NotFoundException;
import com.mediscreen.patientHistory.model.PatientHistory;
import com.mediscreen.patientHistory.repository.PatientHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PatientHistoryServiceTest {

    @InjectMocks
    private PatientHistoryService patientHistoryService;

    @Mock
    private PatientHistoryRepository patientHistoryRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create() {
        PatientHistory patientHistory = new PatientHistory();
        patientHistory.setId("1");
        patientHistory.setPatId(1);
        patientHistory.setNote("Test note");
        patientHistory.setDate(LocalDate.now());

        when(patientHistoryRepository.save(any(PatientHistory.class))).thenReturn(patientHistory);

        PatientHistory result = patientHistoryService.create(1, "Test note");

        assertEquals(patientHistory, result);
        verify(patientHistoryRepository).save(any(PatientHistory.class));
    }

    @Test
    void delete() {
        String id = "testId";
        doNothing().when(patientHistoryRepository).deleteById(id);
        patientHistoryService.delete(id);
        verify(patientHistoryRepository, times(1)).deleteById(id);
    }

    @Test
    void getNotesByPatientId() {
        PatientHistory note1 = new PatientHistory();
        note1.setId("note1");
        note1.setPatId(1);
        note1.setNote("Test note 1");

        PatientHistory note2 = new PatientHistory();
        note2.setId("note2");
        note2.setPatId(1);
        note2.setNote("Test note 2");

        when(patientHistoryRepository.findByPatId(1)).thenReturn(Arrays.asList(note1, note2));

        List<PatientHistory> notes = patientHistoryService.getNotesByPatientId(1);
        assertEquals(2, notes.size());
        verify(patientHistoryRepository, times(1)).findByPatId(1);
    }

    @Test
    void getNoteById() {
        String id = "testId";
        PatientHistory note = new PatientHistory();
        note.setId(id);
        note.setPatId(1);
        note.setNote("Test note");

        when(patientHistoryRepository.findById(id)).thenReturn(Optional.of(note));

        PatientHistory fetchedNote = patientHistoryService.getNoteById(id);
        assertEquals(note, fetchedNote);
        verify(patientHistoryRepository, times(1)).findById(id);
    }

    @Test
    void getNoteByIdNotFound() {
        String id = "testId";
        when(patientHistoryRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> patientHistoryService.getNoteById(id));
    }

    @Test
    void updateNote() {
        String id = "testId";
        PatientHistory existingNote = new PatientHistory();
        existingNote.setId(id);
        existingNote.setPatId(1);
        existingNote.setNote("Old Note");

        PatientHistory updatedNote = new PatientHistory();
        updatedNote.setId(id);
        updatedNote.setPatId(1);
        updatedNote.setNote("New Note");

        when(patientHistoryRepository.findById(id)).thenReturn(Optional.of(existingNote));
        when(patientHistoryRepository.save(existingNote)).thenReturn(updatedNote);

        PatientHistory result = patientHistoryService.updateNote(updatedNote);
        assertEquals(updatedNote.getId(), result.getId());
        assertEquals(updatedNote.getPatId(), result.getPatId());
        assertEquals(updatedNote.getNote(), result.getNote());
        assertEquals(updatedNote.getDate(), result.getDate());
        verify(patientHistoryRepository, times(1)).findById(id);
        verify(patientHistoryRepository, times(1)).save(existingNote);
    }
}
