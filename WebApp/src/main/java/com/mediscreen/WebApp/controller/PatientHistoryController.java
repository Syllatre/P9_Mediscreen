package com.mediscreen.WebApp.controller;

import com.mediscreen.WebApp.mapper.PatientHistoryListDTOMapper;
import com.mediscreen.WebApp.model.PatientHistoryBean;
import com.mediscreen.WebApp.proxy.MicroservicePatientHistoryProxy;
import com.mediscreen.WebApp.proxy.MicroservicePatientProxy;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
@Slf4j
public class PatientHistoryController {

    MicroservicePatientHistoryProxy patientHistoryProxy;

    MicroservicePatientProxy patientProxy;

    @GetMapping(value = "/patHistory/list/{patId}")
    public String getAllNotesByPatientId(@PathVariable("patId") Integer patId, Model model) {

        model.addAttribute("patHistory", PatientHistoryListDTOMapper.INSTANCE.from(patientProxy.getPatientById(patId.longValue()), patientHistoryProxy.getAllNotesByPatientId(patId)));
        log.info("Display all patient history");
        return "/patHistory/list";
    }


    @GetMapping("/patHistory/add")
    public String addNoteForm(Model model) {
        PatientHistoryBean patHistory = new PatientHistoryBean();
        model.addAttribute("patHistory", patHistory);
        log.info("return new form");
        return "patHistory/add";
    }

    @PostMapping("/patHistory/validate")
    public String validate(@Parameter(description = "Note history information to be recorded", required = true) @Valid @RequestParam("patId") Integer patId, @RequestParam String note, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.info("informations is not valid");
            return "patHistory/add";
        }
        patientHistoryProxy.create(patId, note);
        log.info("Note was add");
        model.addAttribute("patHistory", patientHistoryProxy.getAllNotesByPatientId(patId));
        return "redirect:/patHistory/list" + patId;
    }
}
