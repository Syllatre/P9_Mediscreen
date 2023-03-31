package com.mediscreen.WebApp.proxy;


import com.mediscreen.WebApp.model.PatientBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "PatientsManagement", url = "${feign.url.patient}")
public interface MicroservicePatientProxy {


    @GetMapping(value = "/patients/list")
    List<PatientBean> getAllPatient();


    @PostMapping(value = "/patients/add")
    PatientBean addPatient(@RequestBody PatientBean patient);


    @PutMapping(value = "/patients/update")
    PatientBean updatePatient(@RequestBody PatientBean patient);


    @DeleteMapping(value = "/patients/delete/{idPatient}")
    void deletePatient(@PathVariable("idPatient") Integer id);

    @GetMapping("/patients/{idPatient}")
    PatientBean getPatientById(@PathVariable("idPatient") Integer id);

}
