package com.mediscreen.patient.controller;

import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@Tag(name = "Patient Management System", description = "API for managing patient")
public class PatientController {
    PatientService patientService;


    @Operation(summary = "Shows all patients", tags = {"Patient Management System"})
    @ApiResponse(responseCode = "200", description = "Succes | OK")
    @GetMapping("/patients/list")
    public List<Patient> getAllPatient() {
        List<Patient> patient = patientService.findAll();
        log.info("Display patient List");
        return patient;
    }

    @Operation(summary = "Shows all patients", tags = {"Patient Management System"})
    @ApiResponse(responseCode = "200", description = "Succes | OK")
    @ApiResponse(responseCode = "404", description = "Patient not found")
    @GetMapping("/patients/{idPatient}")
    public Patient getPatientById(@Parameter(description = "Patient ID", required = true) @PathVariable("idPatient") Long id) {
        Patient patient = patientService.findById(id);
        log.info("Display patient by Id");
        return patient;
    }


    @PostMapping("/patients/add")
    @Operation(summary = "Add patient", description = "Validates the information from the add new patient form and adds it to the database.", tags = {"Patient Management System"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Patient added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid information in the form")
    })
    @ResponseStatus(HttpStatus.CREATED)
    public Patient addPatient(@Parameter(description = "Personal patient information to be recorded", required = true) @Valid @RequestBody Patient patient) {

        Patient addPatient = patientService.create(patient);
        log.info("Patient " + patient.getFirstName() + " " + patient.getSurname() + " was add");
        return addPatient;
    }


    @PutMapping("/patients/update")
    @Operation(summary = "Update Patient", description = "Validates the information of the modification form of a patient and updates the data of the corresponding patient.", tags = {"Patient Management System"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully modified patient"),
            @ApiResponse(responseCode = "404", description = "Patient not found")
    })
    public Patient updatePatient(@Parameter(description = "Personal patient information to modify", required = true) @Valid @RequestBody Patient patient) {
        Patient updatedPatient = patientService.updatePatient(patient);
        return updatedPatient;
    }

    @Operation(summary = "Delete Patient", description = "Deletes the patient corresponding to the given identifier.", tags = {"Patient Management System"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "404", description = "Patient not found")
    })
    @DeleteMapping("/patients/delete/{idPatient}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePatient(@Parameter(description = "Patient ID", required = true) @PathVariable("idPatient") Long id) {
        patientService.delete(id);
        log.info("Patient " + id + " was deleted");
    }
}
