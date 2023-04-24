package com.mediscreen.WebApp.proxy;

import com.mediscreen.WebApp.model.PatientHistoryBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "patientHistory", url = "${proxy.note}")
public interface MicroservicePatientHistoryProxy {


    @GetMapping(value = "/pathistory/list/{patId}")
    List<PatientHistoryBean> getAllNotesByPatientId(@PathVariable("patId") Integer patId);


    @GetMapping("/pathistory/NoteById/{id}")
    PatientHistoryBean geNotesById(@PathVariable("id") String id);


    @PostMapping("/pathistory/add")
    public PatientHistoryBean create(@RequestParam("patId") Integer patId, @RequestParam String note);


    @PutMapping("/pathistory/update")
    PatientHistoryBean updatePatientHistory(@RequestBody PatientHistoryBean patientHistory);

    @DeleteMapping("/pathistory/delete/{id}")
    void deletePatientNote(@PathVariable("id") String id);

}
