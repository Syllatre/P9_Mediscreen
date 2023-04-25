package com.mediscreen.WebApp.controller;

import com.mediscreen.WebApp.mapper.PatientHistoryDTOMapper;
import com.mediscreen.WebApp.mapper.PatientHistoryListDTOMapper;
import com.mediscreen.WebApp.model.DTO.PatientHistoryDTO;
import com.mediscreen.WebApp.model.PatientHistoryBean;
import com.mediscreen.WebApp.proxy.MicroservicePatientHistoryProxy;
import com.mediscreen.WebApp.proxy.MicroservicePatientProxy;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
@Slf4j
public class PatientHistoryController {

    MicroservicePatientHistoryProxy patientHistoryProxy;

    MicroservicePatientProxy patientProxy;

    @GetMapping("/pathistory/list/{idPatient}")
    public String getAllNotesByPatientId(@PathVariable("idPatient") Integer idPatient, Model model) {

        model.addAttribute("patHistory", PatientHistoryListDTOMapper.INSTANCE.from(patientProxy.getPatientById(idPatient), patientHistoryProxy.getAllNotesByPatientId(idPatient)));
        log.info("Display all patient history");
        return "pathistory/list";
    }


    @GetMapping("/pathistory/add/{idPatient}")
    public String addNoteForm(@PathVariable("idPatient") Integer idPatient,  Model model) {
        model.addAttribute("patientHistory", PatientHistoryDTOMapper.INSTANCE.from(patientProxy.getPatientById(idPatient),new PatientHistoryBean()));
        log.info("return new form");
        return "pathistory/add";
    }

    @PostMapping("/pathistory/add/{idPatient}")
    public String validate(@PathVariable("idPatient") Integer idPatient, @ModelAttribute("patientHistory") @Valid PatientHistoryDTO patientHistoryDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.info("informations is not valid");
            return "pathistory/add";
        }
        patientHistoryProxy.create(idPatient, patientHistoryDTO.getNoteText());
        log.info("Note was add");
        model.addAttribute("patHistory", patientHistoryProxy.getAllNotesByPatientId(idPatient));
        return "redirect:/pathistory/list/" + idPatient;
    }

    @GetMapping("/pathistory/delete/{idPatient}/{id}")
    public String deletePatientHistory(@PathVariable("idPatient") Integer idPatient,@PathVariable("id") String id,Model model) {
        patientHistoryProxy.deletePatientNote(id);
        log.info("Patient " + id + " was deleted");
        return "redirect:/pathistory/list/"+idPatient;
    }


    @GetMapping("/pathistory/update/{idPatient}/{id}")
    public String getNoteById(@PathVariable("idPatient") Integer idPatient,@PathVariable("id") String id, Model model) {
        model.addAttribute("patientHistory",PatientHistoryDTOMapper.INSTANCE.from(patientProxy.getPatientById(idPatient),patientHistoryProxy.geNotesById(id)));
        return "pathistory/update";
    }

    @PostMapping("/pathistory/update/{idPatient}/{id}")
    public String updateNote(@PathVariable("idPatient") Integer idPatient,@PathVariable("id") String id,  @ModelAttribute("patientHistory") @Valid PatientHistoryDTO patientHistory, BindingResult result, Model model){
        if (result.hasErrors()) {
            log.info("informations is not valid");
            return "pathistory/update";
        }
        PatientHistoryBean updated = patientHistoryProxy.updatePatientHistory(new PatientHistoryBean(id,idPatient,patientHistory.getNoteText(),null));
        if (updated == null || updated.getId() == null) {
            System.out.println("id not found");
        }

        model.addAttribute("patientHistories", patientHistoryProxy.getAllNotesByPatientId(idPatient));
        log.info("Patient note " + patientHistory + " was updated");
        return "redirect:/pathistory/list/"+patientHistory.getIdPatient();
    }

}
