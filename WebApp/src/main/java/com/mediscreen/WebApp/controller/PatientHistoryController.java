package com.mediscreen.WebApp.controller;

import com.mediscreen.WebApp.mapper.PatientHistoryDTOMapper;
import com.mediscreen.WebApp.mapper.PatientHistoryListDTOMapper;
import com.mediscreen.WebApp.model.DTO.PatientHistoryDTO;
import com.mediscreen.WebApp.model.PatientBean;
import com.mediscreen.WebApp.model.PatientHistoryBean;
import com.mediscreen.WebApp.proxy.MicroservicePatientHistoryProxy;
import com.mediscreen.WebApp.proxy.MicroservicePatientProxy;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
@Slf4j
public class PatientHistoryController {

    MicroservicePatientHistoryProxy patientHistoryProxy;

    MicroservicePatientProxy patientProxy;

    @GetMapping(value = "/patHistory/list/{idPatient}")
    public String getAllNotesByPatientId(@PathVariable("idPatient") Integer idPatient, Model model) {

        model.addAttribute("patHistory", PatientHistoryListDTOMapper.INSTANCE.from(patientProxy.getPatientById(idPatient), patientHistoryProxy.getAllNotesByPatientId(idPatient)));
        log.info("Display all patient history");
        return "/patHistory/list";
    }


    @GetMapping("/patHistory/add/{idPatient}")
    public String addNoteForm(@PathVariable("idPatient") Integer idPatient,  Model model) {
        model.addAttribute("patientHistory", PatientHistoryDTOMapper.INSTANCE.from(patientProxy.getPatientById(idPatient),new PatientHistoryBean()));
        log.info("return new form");
        return "patHistory/add";
    }

    @PostMapping("/patHistory/add/{idPatient}")
    public String validate(@PathVariable("idPatient") Integer idPatient, @ModelAttribute("patientHistory") @Valid PatientHistoryDTO patientHistoryDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.info("informations is not valid");
            return "patHistory/add";
        }
        patientHistoryProxy.create(idPatient, patientHistoryDTO.getNoteText());
        log.info("Note was add");
        model.addAttribute("patHistory", patientHistoryProxy.getAllNotesByPatientId(idPatient));
        return "redirect:/patHistory/list/" + idPatient;
    }

    @GetMapping("/patHistory/delete/{idPatient}/{id}")
    public String deletePatientHistory(@PathVariable("idPatient") Integer idPatient,@PathVariable("id") String id,Model model) {
        patientHistoryProxy.deletePatientNote(id);
        log.info("Patient " + id + " was deleted");
        return "redirect:/patHistory/list/"+idPatient;
    }


    @GetMapping("/patHistory/update/{idPatient}/{id}")
    public String getNoteById(@PathVariable("idPatient") Integer idPatient,@PathVariable("id") String id, Model model) {
        model.addAttribute("patientHistory",PatientHistoryDTOMapper.INSTANCE.from(patientProxy.getPatientById(idPatient),patientHistoryProxy.geNotesById(id)));
        return "/patHistory/update";
    }

    @PostMapping("/patHistory/update/{idPatient}/{id}")
    public String updateNote(@PathVariable("idPatient") Integer idPatient,@PathVariable("id") String id,  @ModelAttribute("patientHistory") @Valid PatientHistoryDTO patientHistory, BindingResult result, Model model){
        if (result.hasErrors()) {
            log.info("informations is not valid");
            return "patHistory/update";
        }
        PatientHistoryBean updated = patientHistoryProxy.updatePatientHistory(new PatientHistoryBean(id,idPatient,patientHistory.getNoteText(),null));
        if (updated.getId() == null) {
            System.out.println("id not found");
        }

        model.addAttribute("patientHistories", patientHistoryProxy.getAllNotesByPatientId(idPatient));
        log.info("Patient note " + patientHistory + " was updated");
        return "redirect:/patHistory/list/"+patientHistory.getIdPatient();
    }

}
