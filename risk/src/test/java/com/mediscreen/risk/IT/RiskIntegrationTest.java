package com.mediscreen.risk.IT;

import com.mediscreen.risk.model.DTO.PatientDiabetes;
import com.mediscreen.risk.model.PatientBean;
import com.mediscreen.risk.model.PatientHistoryBean;
import com.mediscreen.risk.proxy.MicroservicePatientHistoryProxy;
import com.mediscreen.risk.proxy.MicroservicePatientProxy;
import com.mediscreen.risk.service.RiskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RiskIntegrationTest {

    @Autowired
    private RiskService riskService;

    @MockBean
    private MicroservicePatientProxy microservicePatientProxy;

    @MockBean
    private MicroservicePatientHistoryProxy microservicePatientHistoryProxy;

    @Test
    public void testGetPatientDiabetesIntegration() {
        PatientBean patientBean = new PatientBean(1, "John", "Doe", LocalDate.of(1995, 1, 1), "M", "555-555-5555", "123 Main St");
        when(microservicePatientProxy.getPatientById(1)).thenReturn(patientBean);

        List<PatientHistoryBean> patientNotes = Arrays.asList(
                new PatientHistoryBean("1", 1, "note with hémoglobine a1c", LocalDate.now()),
                new PatientHistoryBean("2", 1, "microalbumine taille", LocalDate.now()),
                new PatientHistoryBean("3", 1, "note without poids", LocalDate.now())
        );
        when(microservicePatientHistoryProxy.getAllNotes()).thenReturn(patientNotes);

        int patientId = 1;
        PatientDiabetes patientDiabetes = riskService.getPatientDiabetes(patientId);

        assertEquals("John", patientDiabetes.getFirstName());
        assertEquals("Doe", patientDiabetes.getSurname());
        assertEquals(riskService.calculateAge(patientBean.getDateOfBirthday()), patientDiabetes.getAge());
        assertEquals("In Danger", patientDiabetes.getDiabetesAssessments());
    }
}

