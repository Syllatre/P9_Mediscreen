package com.mediscreen.WebApp.proxy;

import com.mediscreen.WebApp.model.PatientDiabetesBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "risk", url = "${feign.url.risk}")
public interface MicroservicePatientRiskProxy {
    @GetMapping(value = "/assess/risk/{idPatient}")
    public PatientDiabetesBean getRiskById(@PathVariable("idPatient") Integer id);
}
