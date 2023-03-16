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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
@Slf4j
@Tag(name = "Patient Management System", description = "API for managing patient")
public class PatientController {
    PatientService patientService;


    @Operation(summary = "Affiche tous les patients", tags = {"Patient Management System"})
    @ApiResponse(responseCode = "200", description = "Succes | OK")
    @GetMapping("/patient/list")
    public String home(Model model) {
        List<Patient> patient = patientService.findAll();
        model.addAttribute("patient", patient);
        log.info("Display patient List");
        return "patient/list";
    }

    @GetMapping("/patient/add")
    @Operation(summary = "Afficher le formulaire d'ajout de patient", description = "Affiche un formulaire vide pour l'ajout d'un nouveau patient.", tags = {"Patient Management System"})
    @ApiResponse(responseCode = "200", description = "Succes | OK")
    public String addPatientForm(Model model) {
        Patient patient = new Patient();
        model.addAttribute("patient", patient);
        log.info("return new form");
        return "patient/add";
    }

    @PostMapping("/patient/validate")
    @Operation(summary = "Valider l'ajout d'un patient", description = "Valide les informations du formulaire d'ajout d'un nouveau patient et l'ajoute à la base de données.", tags = {"Patient Management System"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Patient ajouté avec succès"),
            @ApiResponse(responseCode = "400", description = "Informations invalides dans le formulaire")
    })
    public String validate(@Parameter(description = "Informations personnelle du patient à enregistrer", required = true) @Valid Patient patient, BindingResult result, Model model) {
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
    @Operation(summary = "Afficher le formulaire de modification de patient", description = "Affiche un formulaire prérempli avec les informations du patient à modifier.", tags = {"Patient Management System"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Formulaire de modification affiché avec succès"),
            @ApiResponse(responseCode = "404", description = "Patient non trouvé")
    })
    public String showUpdateForm(@Parameter(description = "Id du patient", required = true) @PathVariable("idPatient") Long idPatient, Model model) {
        Patient patient = patientService.findById(idPatient);
        model.addAttribute("patient", patient);
        log.info("return form with " + patient + " to update it");
        return "patient/update";
    }

    @PostMapping("/patient/update/{idPatient}")
    @Operation(summary = "Valider la modification d'un patient", description = "Valide les informations du formulaire de modification d'un patient et met à jour les données du patient correspondant.", tags = {"Patient Management System"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Patient modifié avec succès"),
            @ApiResponse(responseCode = "400", description = "Informations invalides dans le formulaire"),
            @ApiResponse(responseCode = "404", description = "Patient non trouvé")
    })
    public String updatePatient(@Parameter(description = "Id du patient", required = true) @PathVariable("idPatient") Long idPatient,
                                @Parameter(description = "Informations personnelle du patient à modifier", required = true) @Valid Patient patient,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.info("informations is not valid");
            return "patient/update";
        }
        Boolean updated = patientService.updatePatient(idPatient, patient);
        if (!updated) {
            model.addAttribute("patient", patientService.findAll());
            log.info("Patient " + patient + " was updated");
        }
        return "redirect:/patient/list";
    }

    @Operation(summary = "Supprimer un patient", description = "Supprime le patient correspondant à l'identifiant donné.", tags = {"Patient Management System"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "404", description = "Patient non trouvé")
    })
    @GetMapping("/patient/delete/{idPatient}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deletePatient(@Parameter(description = "Id du patient", required = true) @PathVariable("idPatient") Long id, Model model) {
        patientService.delete(id);
        log.info("Patient " + id + " was deleted");
        return "redirect:/patient/list";
    }
}
