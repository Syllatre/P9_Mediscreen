package com.mediscreen.risk.service;

import com.mediscreen.risk.constant.Keyword;
import com.mediscreen.risk.model.DTO.PatientDiabetes;
import com.mediscreen.risk.model.PatientBean;
import com.mediscreen.risk.model.PatientHistoryBean;
import com.mediscreen.risk.proxy.MicroservicePatientHistoryProxy;
import com.mediscreen.risk.proxy.MicroservicePatientProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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

    @Test
    public void testGetPatientDiabetes() {
        // Prepare test data
        int patientId = 1;
        PatientBean patientBean = new PatientBean(patientId, "John", "Doe", LocalDate.of(1995, 1, 1), "M", "555-555-5555", "123 Main St");

        when(microservicePatientProxy.getPatientById(patientId)).thenReturn(patientBean);

        // Mock the getRisk method to avoid using the actual implementation in this test
        RiskService spyRiskService = Mockito.spy(riskService);
        when(spyRiskService.getRisk(patientBean)).thenReturn("In Danger");

        // Call the method
        PatientDiabetes patientDiabetes = spyRiskService.getPatientDiabetes(patientId);

        // Verify results
        assertEquals("In Danger", patientDiabetes.getDiabetesAssessments());
        assertEquals(patientBean.getFirstName(), patientDiabetes.getFirstName());
        assertEquals(patientBean.getSurname(), patientDiabetes.getSurname());
        assertEquals(riskService.calculateAge(patientBean.getDateOfBirthday()), patientDiabetes.getAge());
    }

    @Test
    public void testGetRiskNone() {
        PatientBean patientBean = new PatientBean(1, "John", "Doe", LocalDate.of(1995, 1, 1), "M", "555-555-5555", "123 Main St");
        List<PatientHistoryBean> patientNotes = Arrays.asList(
                new PatientHistoryBean("1", 1, "note without keyword", LocalDate.now())
        );

        when(microservicePatientHistoryProxy.getAllNotes()).thenReturn(patientNotes);
        String risk = riskService.getRisk(patientBean);
        assertEquals("None", risk);
    }

    @Test
    public void testGetRiskBorderline() {
        PatientBean patientBean = new PatientBean(1, "Jane", "Doe", LocalDate.of(1985, 1, 1), "F", "555-555-5555", "123 Main St");
        List<PatientHistoryBean> patientNotes = Arrays.asList(
                new PatientHistoryBean("1", 1, "hémoglobine a1c", LocalDate.now()),
                new PatientHistoryBean("2", 1, "taille", LocalDate.now())
        );

        when(microservicePatientHistoryProxy.getAllNotes()).thenReturn(patientNotes);
        String risk = riskService.getRisk(patientBean);
        assertEquals("Borderline", risk);
    }

    @Test
    public void testGetRiskEarlyOnsetMaleYoungerThan30() {
        PatientBean patientBean = new PatientBean(1, "John", "Doe", LocalDate.of(1995, 1, 1), "M", "555-555-5555", "123 Main St");
        List<PatientHistoryBean> patientNotes = Arrays.asList(
                new PatientHistoryBean("1", 1, "hémoglobine a1c", LocalDate.now()),
                new PatientHistoryBean("2", 1, "taille", LocalDate.now()),
                new PatientHistoryBean("3", 1, "poids", LocalDate.now()),
                new PatientHistoryBean("4", 1, "fumeur", LocalDate.now()),
                new PatientHistoryBean("5", 1, "anormal", LocalDate.now())
        );

        when(microservicePatientHistoryProxy.getAllNotes()).thenReturn(patientNotes);
        String risk = riskService.getRisk(patientBean);
        assertEquals("Early onset", risk);
    }

    @Test
    public void testGetRiskEarlyOnsetFemaleYoungerThan30() {
        PatientBean patientBean = new PatientBean(1, "Jane", "Doe", LocalDate.of(1995, 1, 1), "F", "555-555-5555", "123 Main St");
        List<PatientHistoryBean> patientNotes = Arrays.asList(
                new PatientHistoryBean("1", 1, "hémoglobine a1c", LocalDate.now()),
                new PatientHistoryBean("2", 1, "taille", LocalDate.now()),
                new PatientHistoryBean("3", 1, "poids", LocalDate.now()),
                new PatientHistoryBean("4", 1, "fumeur", LocalDate.now()),
                new PatientHistoryBean("5", 1, "anormal", LocalDate.now()),
                new PatientHistoryBean("6", 1, "cholestérol", LocalDate.now()),
                new PatientHistoryBean("7", 1, "vertige", LocalDate.now())
        );

        when(microservicePatientHistoryProxy.getAllNotes()).thenReturn(patientNotes);
        String risk = riskService.getRisk(patientBean);
        assertEquals("Early onset", risk);
    }

    @Test
    public void testGetRiskEarlyOnsetOlderThan30() {
        PatientBean patientBean = new PatientBean(1, "John", "Doe", LocalDate.of(1980, 1, 1), "M", "555-555-5555", "123 Main St");
        List<PatientHistoryBean> patientNotes = Arrays.asList(
                new PatientHistoryBean("1", 1, "hémoglobine a1c", LocalDate.now()),
                new PatientHistoryBean("2", 1, "taille", LocalDate.now()),
                new PatientHistoryBean("3", 1, "poids", LocalDate.now()),
                new PatientHistoryBean("4", 1, "fumeur", LocalDate.now()),
                new PatientHistoryBean("5", 1, "anormal", LocalDate.now()),
                new PatientHistoryBean("6", 1, "cholestérol", LocalDate.now()),
                new PatientHistoryBean("7", 1, "vertige", LocalDate.now()),
                new PatientHistoryBean("8", 1, "rechute", LocalDate.now())
        );

        when(microservicePatientHistoryProxy.getAllNotes()).thenReturn(patientNotes);
        String risk = riskService.getRisk(patientBean);
        assertEquals("Early onset", risk);
    }

    @Test
    public void testGetRiskInDangerMaleYoungerThan30() {
        PatientBean patientBean = new PatientBean(1, "John", "Doe", LocalDate.of(1995, 1, 1), "M", "555-555-5555", "123 Main St");
        List<PatientHistoryBean> patientNotes = Arrays.asList(
                new PatientHistoryBean("1", 1, "hémoglobine a1c", LocalDate.now()),
                new PatientHistoryBean("2", 1, "taille", LocalDate.now()),
                new PatientHistoryBean("3", 1, "poids", LocalDate.now())
        );

        when(microservicePatientHistoryProxy.getAllNotes()).thenReturn(patientNotes);
        String risk = riskService.getRisk(patientBean);
        assertEquals("In Danger", risk);
    }
    @Test
    public void testGetRiskInDangerFemaleYoungerThan30() {
        PatientBean patientBean = new PatientBean(1, "Jane", "Doe", LocalDate.of(1995, 1, 1), "F", "555-555-5555", "123 Main St");
        List<PatientHistoryBean> patientNotes = Arrays.asList(
                new PatientHistoryBean("1", 1, "hémoglobine a1c", LocalDate.now()),
                new PatientHistoryBean("2", 1, "taille", LocalDate.now()),
                new PatientHistoryBean("3", 1, "poids", LocalDate.now()),
                new PatientHistoryBean("4", 1, "fumeur", LocalDate.now())
        );

        when(microservicePatientHistoryProxy.getAllNotes()).thenReturn(patientNotes);

        String risk = riskService.getRisk(patientBean);
        assertEquals("In Danger", risk);
    }

    @Test
    public void testGetRiskInDangerOlderThan30() {
        PatientBean patientBean = new PatientBean(1, "John", "Doe", LocalDate.of(1980, 1, 1), "M", "555-555-5555", "123 Main St");
        List<PatientHistoryBean> patientNotes = Arrays.asList(
                new PatientHistoryBean("1", 1, "hémoglobine a1c", LocalDate.now()),
                new PatientHistoryBean("2", 1, "taille", LocalDate.now()),
                new PatientHistoryBean("3", 1, "poids", LocalDate.now()),
                new PatientHistoryBean("4", 1, "fumeur", LocalDate.now()),
                new PatientHistoryBean("5", 1, "anormal", LocalDate.now()),
                new PatientHistoryBean("6", 1, "cholestérol", LocalDate.now())
        );

        when(microservicePatientHistoryProxy.getAllNotes()).thenReturn(patientNotes);
        String risk = riskService.getRisk(patientBean);
        assertEquals("In Danger", risk);
    }
}
