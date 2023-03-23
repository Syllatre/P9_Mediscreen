package com.mediscreen.WebApp.controller;

import com.mediscreen.WebApp.model.PatientBean;
import com.mediscreen.WebApp.proxy.MicroservicePatientProxy;
import io.swagger.v3.oas.annotations.Parameter;
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
public class PatientController {

    MicroservicePatientProxy patientService;


    @GetMapping("/patients/list")
    public String getAllPatient(Model model) {
        List<PatientBean> patient = patientService.getAllPatient();
        model.addAttribute("patient",patient);
        log.info("Display patient List");
        return "patients/list";
    }

    @GetMapping("/patients/add")
    public String addPatientForm(Model model) {
        PatientBean patient = new PatientBean();
        model.addAttribute("patient", patient);
        log.info("return new form");
        return "patients/add";
    }

    @PostMapping("/patients/validate")
    public String validate(@Parameter(description = "Personal patient information to be recorded", required = true) @Valid PatientBean patient, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.info("informations is not valid");
            return "patients/add";
        }
        patientService.addPatient(patient);
        log.info("Patient " + patient.getFirstName() + " " + patient.getSurname() + " was add");
        model.addAttribute("patient", patientService.getAllPatient());
        return "redirect:/patients/list";
    }

    @GetMapping("/patients/update/{idPatient}")
    public String showUpdateForm(@Parameter(description = "Patient ID", required = true) @PathVariable("idPatient") Long idPatient, Model model) {
        PatientBean patient = patientService.getPatientById(idPatient);
        model.addAttribute("patient", patient);
        log.info("return form with " + patient + " to update it");
        return "patients/update";
    }

    @PutMapping("/patients/update/{idPatient}")
    public String updatePatient(@Parameter(description = "Patient ID", required = true) @PathVariable("idPatient") Long idPatient,
                                @Parameter(description = "Personal patient information to modify", required = true) @Valid PatientBean patient,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.info("informations is not valid");
            return "patients/update";
        }
        PatientBean updated = patientService.updatePatient(idPatient, patient);
        if (updated != null) {
            model.addAttribute("patient", patientService.getAllPatient());
            log.info("Patient " + patient + " was updated");
        }
        return "redirect:/patients/list";
    }

    @DeleteMapping("/patients/delete/{idPatient}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deletePatient(@Parameter(description = "Patient ID", required = true) @PathVariable("idPatient") Long id, Model model) {
        patientService.deletePatient(id);
        log.info("Patient " + id + " was deleted");
        return "redirect:/patients/list";
    }
}
