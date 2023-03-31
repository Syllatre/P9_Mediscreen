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
        List<PatientBean> patients = patientService.getAllPatient();
        model.addAttribute("patients",patients);
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
    public String validate(@Valid @ModelAttribute("patient") PatientBean patient, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.info("informations is not valid");
            return "patients/add";
        }
        patientService.addPatient(patient);
        model.addAttribute("patients", patientService.getAllPatient());

        log.info("Patient " + patient.getFirstName() + " " + patient.getSurname() + " was add");

        return "redirect:/patients/list";
    }

    @GetMapping("/patients/update/{idPatient}")
    public String showUpdateForm(@PathVariable("idPatient") Integer idPatient, Model model) {
        PatientBean patient = patientService.getPatientById(idPatient);
        model.addAttribute("patient", patient);
        log.info("return form with " + patient + " to update it");
        return "patients/update";
    }

    @PostMapping("/patients/update/{idPatient}")
    public String updatePatient(@PathVariable("idPatient") Integer idPatient, @Valid @ModelAttribute("patient") PatientBean patient, BindingResult result, Model model) {

        if (result.hasErrors()) {
            log.info("informations is not valid");
            return "patients/update";
        }
        PatientBean updated = patientService.updatePatient(patient);
        if (updated.getIdPatient() == null) {
            System.out.println("id not found");
        }

        model.addAttribute("patients", patientService.getAllPatient());
        log.info("Patient " + patient + " was updated");
        return "redirect:/patients/list";
    }

    @GetMapping("/patients/delete/{idPatient}")
    public String deletePatient(@PathVariable("idPatient") Integer id,Model model) {
        patientService.deletePatient(id);
        model.addAttribute("patients", patientService.getAllPatient());
        log.info("Patient " + id + " was deleted");
        return "redirect:/patients/list";
    }
}
