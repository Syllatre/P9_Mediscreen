package com.mediscreen.risk.service;

import com.mediscreen.risk.constant.Keyword;
import com.mediscreen.risk.model.PatientBean;
import com.mediscreen.risk.model.PatientHistoryBean;
import com.mediscreen.risk.proxy.MicroservicePatientHistoryProxy;
import com.mediscreen.risk.proxy.MicroservicePatientProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class RiskServiceTest {

    @InjectMocks
    private RiskService riskService;

    @Mock
    private MicroservicePatientProxy microservicePatientProxy;

    @Mock
    private MicroservicePatientHistoryProxy microservicePatientHistoryProxy;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetRisk() {
        // Prepare test data
        PatientBean patientBean = new PatientBean(1, "John", "Doe", LocalDate.of(1995, 1, 1), "M", "555-555-5555", "123 Main St");
        List<PatientHistoryBean> patientNotes = Arrays.asList(
                new PatientHistoryBean("1", 1, "note with hémoglobine a1c", LocalDate.now()),
                new PatientHistoryBean("2", 1, "microalbumine taille", LocalDate.now()),
                new PatientHistoryBean("3", 1, "note without poids", LocalDate.now())
        );
//        "hémoglobine a1c",
//                "microalbumine",
//                "taille",
//                "poids",
//                "fumeur",
//                "anormal",
//                "cholestérol",
//                "vertige",
//                "rechute",
//                "réaction",
//                "anticorps"

        when(microservicePatientHistoryProxy.getAllNotes()).thenReturn(patientNotes);

        String risk = riskService.getRisk(patientBean);

        assertEquals("In Danger", risk);
    }

    @Test
    public void testCountNoteByKeyword() {
        List<String> keywordList = Arrays.asList("hémoglobine a1c", "taille", "fumeur");
        List<PatientHistoryBean> notes = Arrays.asList(
                new PatientHistoryBean("1", 1, "note with hémoglobine a1c and taille",LocalDate.now()),
                new PatientHistoryBean("2", 1, "note without hémoglobine a1c",LocalDate.now()),
                new PatientHistoryBean("3", 1, "note with fumeur",LocalDate.now())
        );

        int count = riskService.countNoteByKeyword(notes);

        assertEquals(4, count);
    }

    @Test
    public void testCalculateAge() {
        LocalDate dateOfBirth = LocalDate.of(1990, 4, 17);
        int age = riskService.calculateAge(dateOfBirth);

        int expectedAge = Period.between(dateOfBirth, LocalDate.now()).getYears();
        assertEquals(expectedAge, age);
    }

}
