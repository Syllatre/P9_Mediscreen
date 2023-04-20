package com.mediscreen.WebApp.controller;

import com.mediscreen.WebApp.model.PatientDiabetesBean;
import com.mediscreen.WebApp.proxy.MicroservicePatientRiskProxy;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@AllArgsConstructor
@Slf4j
public class RiskController {
    MicroservicePatientRiskProxy microservicePatientRisk;

    @GetMapping("/assess/risk/{idPatient}")
    public String getRisk(@PathVariable("idPatient") Integer id, Model model) {
        PatientDiabetesBean patientDiabetesBean = microservicePatientRisk.getRiskById(id);
        model.addAttribute("patientDiabetesBean", patientDiabetesBean);
        log.info("return patientDiabetesBean");
        return "assess/risk";
    }
}
