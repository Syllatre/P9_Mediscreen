package com.mediscreen.patient.service;

import com.mediscreen.patient.exception.NotFoundException;
import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.repository.PatientRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class PatientService {

    private PatientRepository patientRepository;

    public Patient create(Patient patient) {
        return patientRepository.save(patient);
    }

    public void delete(Long idPatient) {
        patientRepository.deleteById(idPatient);
    }

    public Patient findById(Long id) {
        return patientRepository.findById(id).orElseThrow(()->{
            log.error("Patient non trouvé");
            return new NotFoundException("patientId " + id + " non trouvé");
        });
    }

    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    public Boolean updatePatient(Long id, Patient patient) {

        Optional<Patient> patientPresent = patientRepository.findById(id);
        if (patientPresent.isPresent()) {
            Patient updatePatient = patientPresent.get();
            updatePatient.setFirstName(patient.getFirstName());
            updatePatient.setSurname(patient.getSurname());
            updatePatient.setDateOfBirthday(patient.getDateOfBirthday());

            patientRepository.save(updatePatient);
            log.debug("Patient " + id + " was updated");
            return true;
        }
        log.debug("the update was failed because the id: " + id + " doesn't exist");
        return false;
    }
}
