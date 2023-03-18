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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
@Slf4j
@Tag(name = "Patient Management System", description = "API for managing patient")
public class PatientController {
    PatientService patientService;


    @Operation(summary = "Shows all patients", tags = {"Patient Management System"})
    @ApiResponse(responseCode = "200", description = "Succes | OK")
    @GetMapping("/patients/list")
    public String home(Model model) {
        List<Patient> patient = patientService.findAll();
        model.addAttribute("patient", patient);
        log.info("Display patient List");
        return "patients/list";
    }

    @GetMapping("/patients/add")
    public String addPatientForm(Model model) {
        Patient patient = new Patient();
        model.addAttribute("patient", patient);
        log.info("return new form");
        return "patients/add";
    }

    @PostMapping("/patients/validate")
    @Operation(summary = "Add patient", description = "Validates the information from the add new patient form and adds it to the database.", tags = {"Patient Management System"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Patient added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid information in the form")
    })
    public String validate(@Parameter(description = "Personal patient information to be recorded", required = true) @Valid Patient patient, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.info("informations is not valid");
            return "patients/add";
        }
        patientService.create(patient);
        log.info("Patient " + patient.getFirstName() + " " + patient.getSurname() + " was add");
        model.addAttribute("patient", patientService.findAll());
        return "redirect:/patients/list";
    }

    @GetMapping("/patients/update/{idPatient}")
    public String showUpdateForm(@Parameter(description = "Patient ID", required = true) @PathVariable("idPatient") Long idPatient, Model model) {
        Patient patient = patientService.findById(idPatient);
        model.addAttribute("patient", patient);
        log.info("return form with " + patient + " to update it");
        return "patients/update";
    }

    @PutMapping("/patients/update/{idPatient}")
    @Operation(summary = "Update Patient", description = "Validates the information of the modification form of a patient and updates the data of the corresponding patient.", tags = {"Patient Management System"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Successfully modified patient"),
            @ApiResponse(responseCode = "400", description = "Invalid information in the form"),
            @ApiResponse(responseCode = "404", description = "Patient not found")
    })
    public String updatePatient(@Parameter(description = "Patient ID", required = true) @PathVariable("idPatient") Long idPatient,
                                @Parameter(description = "Personal patient information to modify", required = true) @Valid Patient patient,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.info("informations is not valid");
            return "patients/update";
        }
        Boolean updated = patientService.updatePatient(idPatient, patient);
        if (!updated) {
            model.addAttribute("patient", patientService.findAll());
            log.info("Patient " + patient + " was updated");
        }
        return "redirect:/patients/list";
    }

    @Operation(summary = "Delete Patient", description = "Deletes the patient corresponding to the given identifier.", tags = {"Patient Management System"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "404", description = "Patient not found")
    })
    @DeleteMapping("/patients/delete/{idPatient}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deletePatient(@Parameter(description = "Patient ID", required = true) @PathVariable("idPatient") Long id, Model model) {
        patientService.delete(id);
        log.info("Patient " + id + " was deleted");
        return "redirect:/patients/list";
    }
}
