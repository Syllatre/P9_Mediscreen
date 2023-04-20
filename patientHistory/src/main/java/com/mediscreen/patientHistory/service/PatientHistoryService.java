package com.mediscreen.patientHistory.service;

import com.mediscreen.patientHistory.exception.NotFoundException;
import com.mediscreen.patientHistory.model.PatientHistory;
import com.mediscreen.patientHistory.repository.PatientHistoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.core.query.Query;

@Slf4j
@AllArgsConstructor
@Service
public class PatientHistoryService {

    private PatientHistoryRepository patientHistoryRepository;

    private MongoTemplate mongoTemplate;

    public PatientHistory create(Integer patId, String note) {
        LocalDate dateNow = LocalDate.now();
        PatientHistory patientHistory = new PatientHistory();
        patientHistory.setDate(dateNow);
        patientHistory.setPatId(patId);
        patientHistory.setNote(note);
        return patientHistoryRepository.save(patientHistory);
    }

    public void delete(String id) {
        patientHistoryRepository.deleteById(id);
    }


    public List<PatientHistory> getNotesByPatientId(Integer patId) {
        List<PatientHistory> patientHistories = patientHistoryRepository.findByPatId(patId);
        log.info("Patient histories retrieved: {}", patientHistories);

        return patientHistories;
    }

    public PatientHistory getNoteById (String id){
        Optional <PatientHistory> note = patientHistoryRepository.findById(id);
        if (note.isEmpty()){
            throw  new NotFoundException("this note doesn't exist");
        }
        return note.get();
    }

    public List<PatientHistory> getAllNotes(){
       return patientHistoryRepository.findAll();
    }
    public PatientHistory updateNote(PatientHistory patientHistory) {

        Optional<PatientHistory> patientHistoryPresent = patientHistoryRepository.findById(patientHistory.getId());
        PatientHistory updatePatientHistory = patientHistoryPresent.get();
        updatePatientHistory.setNote(patientHistory.getNote());
        patientHistoryRepository.save(updatePatientHistory);
        return updatePatientHistory;
    }



    public void insertData(List<PatientHistory> patientHistories) {
        mongoTemplate.insertAll(patientHistories);
    }

    public void removeAllData() {
        mongoTemplate.remove(new Query(), PatientHistory.class);
    }

}
