package com.mediscreen.patientHistory.controller;

import com.mediscreen.patientHistory.model.PatientHistory;
import com.mediscreen.patientHistory.service.PatientHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@Tag(name = "Patient History Management System", description = "API for managing patient history")
public class PatientHistoryController {
    PatientHistoryService patientHistoryService;



    @GetMapping("/patHistory/list/{patId}")
    public List<PatientHistory> getAllNotesByPatientId(@Parameter(description = "Patient ID", required = true) @PathVariable("patId") Integer patId) {
        List<PatientHistory> patientHistories = patientHistoryService.getNotesByPatientId(patId);
        log.info("Patient histories returned by the controller: {}", patientHistories);
        return patientHistories;

    }

    @Operation(summary = "Shows Note by Id", tags = {"Patient History Management System"})
    @ApiResponse(responseCode = "200", description = "Succes | OK")
    @GetMapping("/patHistory/NoteById/{id}")
    public PatientHistory geNotesById(@Parameter(description = "Note ID", required = true) @PathVariable("id") String id) {
        PatientHistory patientHistories = patientHistoryService.getNoteById(id);
        log.info("Note returned by the controller: {}", patientHistories);
        return patientHistories;

    }


    @PostMapping("/patHistory/add")
    @Operation(summary = "Add patient note", description = "Validates the information from the add new patient note form and adds it to the database.", tags = {"Patient History Management System"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Patient note added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid information in the form")
    })
    @ResponseStatus(HttpStatus.CREATED)
    public PatientHistory create(@Parameter(description = "Patient ID", required = true) @RequestParam("patId") Integer patId,
            @Parameter(description = "Patient note information to be recorded", required = true) @Valid @RequestParam String note) {
        return patientHistoryService.create(patId,note);
    }


    @PutMapping("/patHistory/update")
    @Operation(summary = "Update Patient history", description = "Validates the information of the modification form of a patient and updates the data of the corresponding patient.", tags = {"Patient History Management System"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Successfully modified patient"),
            @ApiResponse(responseCode = "400", description = "Invalid information in the form"),
            @ApiResponse(responseCode = "404", description = "Patient note not found")
    })
    public PatientHistory updatePatientHistory(@Parameter(description = "Personal patient history information to modify", required = true) @Valid @RequestBody PatientHistory patientHistory) {
        return patientHistoryService.updateNote(patientHistory);

    }

    @Operation(summary = "Delete Patient Note", description = "Delete note of patient history", tags = {"Patient History Management System"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "404", description = "Patient not found")
    })
    @DeleteMapping("/patHistory/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePatientNote(@Parameter(description = "Note ID", required = true) @PathVariable("id") String id) {
        patientHistoryService.delete(id);
        log.info("Patient Note " +id+ " was deleted");

    }
}
