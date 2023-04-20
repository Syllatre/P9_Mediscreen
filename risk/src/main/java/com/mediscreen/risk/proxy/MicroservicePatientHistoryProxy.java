package com.mediscreen.risk.proxy;

import com.mediscreen.risk.model.PatientHistoryBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "patientHistory", url = "${feign.url.history}")
public interface MicroservicePatientHistoryProxy {

    @GetMapping("/patHistory/list")
    public List<PatientHistoryBean> getAllNotes();
}
