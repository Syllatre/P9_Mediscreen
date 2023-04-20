package com.mediscreen.risk.controller;

import com.mediscreen.risk.model.DTO.PatientDiabetes;
import com.mediscreen.risk.model.PatientBean;
import com.mediscreen.risk.proxy.MicroservicePatientProxy;
import com.mediscreen.risk.service.RiskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
@Tag(name = "Risk", description = "calculate the risk of diabetes")
public class RiskController {

    private RiskService riskService;

    @Operation(summary = "calculate the risk by patient id", tags = {"Risk"})
    @ApiResponse(responseCode = "200", description = "Succes | OK")
    @GetMapping("/assess/risk/{idPatient}")
    public PatientDiabetes getRiskById(@PathVariable Integer idPatient) {

        return riskService.getPatientDiabetes(idPatient);
    }
}
