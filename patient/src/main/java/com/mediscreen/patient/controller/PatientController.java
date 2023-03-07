package com.mediscreen.patient.controller;

import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.service.PatientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
@Slf4j
public class PatientController {
    PatientService patientService;

    @GetMapping("/patient/list")
    public String home(Model model) {
        List<Patient> patient = patientService.findAll();
        model.addAttribute("patient", patient);
        log.info("Display patient List");
        return "patient/list";
    }

    @GetMapping("/patient/add")
    public String addPatientForm(Model model) {
        Patient patient = new Patient();
        model.addAttribute("patient", patient);
        log.info("return new form");
        return "patient/add";
    }

    @PostMapping("/patient/validate")
    public String validate(@Valid Patient patient, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.info("informations is not valid");
            return "patient/add";
        }
        patientService.create(patient);
        log.info("Patient " + patient.getPrenom() + " " + patient.getNom() + " was add");
        model.addAttribute("patient", patientService.findAll());
        return "redirect:/patient/list";
    }

    @GetMapping("/patient/update/{idPatient}")
    public String showUpdateForm(@PathVariable("idPatient") Long idPatient, Model model) {
        Patient patient = patientService.findById(idPatient);
        model.addAttribute("patient", patient);
        log.info("return form with " + patient + " to update it");
        return "patient/update";
    }

    @PostMapping("/patient/update/{idPatient}")
    public String updatePatient(@PathVariable("idPatient") Long idPatient, @Valid Patient patient,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.info("informations is not valid");
            return "patient/update";
        }
        Boolean updated = patientService.updatePatient(idPatient, patient);
        if (updated) {
            model.addAttribute("patient", patientService.findAll());
            log.info("Patient " + patient + " was updated");
        }
        return "redirect:/patient/list";
    }

    @GetMapping("/patient/delete/{idPatient}")
    public String deletePatient(@PathVariable("idPatient") Long id, Model model) {
        patientService.delete(id);
        log.info("Patient " + id + " was deleted");
        return "redirect:/patient/list";
    }
}
