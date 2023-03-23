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
        Patient patientFind = findById(idPatient);
        if (patientFind == null) {
            throw new NotFoundException("Patient not found");
        }
        patientRepository.deleteById(idPatient);
    }

    public Patient findById(Long id) {
        return patientRepository.findById(id).orElseThrow(() -> {
            log.error("Patient non trouvé");
            return new NotFoundException("patientId " + id + " non trouvé");
        });
    }

    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    public Patient updatePatient(Patient patient) {

        Optional<Patient> patientPresent = patientRepository.findById(patient.getIdPatient());
        Patient updatePatient = patientPresent.get();
        updatePatient.setFirstName(patient.getFirstName());
        updatePatient.setSurname(patient.getSurname());
        updatePatient.setDateOfBirthday(patient.getDateOfBirthday());
        updatePatient.setGender(patient.getGender());
        updatePatient.setAddress(patient.getAddress());
        updatePatient.setPhoneNumber(patient.getPhoneNumber());
        patientRepository.save(updatePatient);
        System.out.println(updatePatient);
        log.debug("Patient " + patient.getIdPatient() + " was updated");
        return updatePatient;


    }
}
