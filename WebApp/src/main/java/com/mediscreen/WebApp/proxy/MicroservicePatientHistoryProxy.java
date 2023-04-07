package com.mediscreen.WebApp.proxy;

import com.mediscreen.WebApp.model.PatientHistoryBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "patientHistory", url = "${feign.url.history}")
public interface MicroservicePatientHistoryProxy {


    @GetMapping(value = "/patHistory/list/{patId}")
    List<PatientHistoryBean> getAllNotesByPatientId(@PathVariable("patId") Integer patId);


    @GetMapping("/patHistory/NoteById/{id}")
    PatientHistoryBean geNotesById(@PathVariable("id") String id);


    @PostMapping("/patHistory/add")
    public PatientHistoryBean create(@RequestParam("patId") Integer patId, @RequestParam String note);


    @PutMapping("/patHistory/update")
    PatientHistoryBean updatePatientHistory(@RequestBody PatientHistoryBean patientHistory);

    @DeleteMapping("/patHistory/delete/{id}")
    void deletePatientNote(@PathVariable("id") String id);

}
