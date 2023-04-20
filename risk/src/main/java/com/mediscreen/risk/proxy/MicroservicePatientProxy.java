package com.mediscreen.risk.proxy;

import com.mediscreen.risk.model.PatientBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "patient", url = "${feign.url.patient}")
public interface MicroservicePatientProxy {

    @GetMapping("/patients/{idPatient}")
    public PatientBean getPatientById(@PathVariable("idPatient") Integer idPatient);
}
