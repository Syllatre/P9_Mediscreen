package com.mediscreen.patient.controller;

import com.mediscreen.patient.exception.NotFoundException;
import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import java.util.Optional;

@Controller
@AllArgsConstructor
@Slf4j
@Tag(name = "Patient Management System", description = "API for managing patient")
public class PatientController {
    PatientService patientService;

    @ApiResponse(responseCode = "200", description = "La liste des patients a été trouvé")
    @Operation(summary = "Affiche tous les patients", tags = {"Patient Management System"})
    @GetMapping("/patient/list")
    public String home(Model model) {
        List<Patient> patient = patientService.findAll();
        model.addAttribute("patient", patient);
        log.info("Display patient List");
        return "patient/list";
    }

    @GetMapping("/patient/add")
    @Operation(summary = "Afficher le formulaire d'ajout de patient", description = "Affiche un formulaire vide pour l'ajout d'un nouveau patient.",tags = {"Patient Management System"})
    public String addPatientForm(Model model) {
        Patient patient = new Patient();
        model.addAttribute("patient", patient);
        log.info("return new form");
        return "patient/add";
    }

    @PostMapping("/patient/validate")
    @Operation(summary = "Valider l'ajout d'un patient", description = "Valide les informations du formulaire d'ajout d'un nouveau patient et l'ajoute à la base de données.",tags = {"Patient Management System"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Patient ajouté avec succès"),
            @ApiResponse(responseCode = "400", description = "Informations invalides dans le formulaire")
    })
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
    @Operation(summary = "Afficher le formulaire de modification de patient", description = "Affiche un formulaire prérempli avec les informations du patient à modifier.",tags = {"Patient Management System"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Formulaire de modification affiché avec succès"),
            @ApiResponse(responseCode = "404", description = "Patient non trouvé")
    })
    public String showUpdateForm(@PathVariable("idPatient") Long idPatient, Model model) {
        Patient patient = patientService.findById(idPatient);
        model.addAttribute("patient", patient);
        log.info("return form with " + patient + " to update it");
        return "patient/update";
    }

    @PostMapping("/patient/update/{idPatient}")
    @Operation(summary = "Valider la modification d'un patient", description = "Valide les informations du formulaire de modification d'un patient et met à jour les données du patient correspondant.",tags = {"Patient Management System"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Patient modifié avec succès"),
            @ApiResponse(responseCode = "400", description = "Informations invalides dans le formulaire"),
            @ApiResponse(responseCode = "404", description = "Patient non trouvé")
    })
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
     else {
        throw new NotFoundException("Patient not found with id " + idPatient);
    }
        return "redirect:/patient/list";
    }
    @Operation(summary = "Supprimer un patient", description = "Supprime le patient correspondant à l'identifiant donné.",tags = {"Patient Management System"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Patient supprimé avec succès"),
            @ApiResponse(responseCode = "404", description = "Patient non trouvé")
    })
    @GetMapping("/patient/delete/{idPatient}")
    public String deletePatient(@PathVariable("idPatient") Long id, Model model) {
        Patient find = patientService.findById(id);
        if (find != null) {
            log.info("Patient " + id + " was deleted");
        } else {
            throw new NotFoundException("Patient not found with id " + id);
        }
        return "redirect:/patient/list";
    }
}
