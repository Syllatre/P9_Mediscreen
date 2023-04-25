package com.mediscreen.WebApp.controller;

import com.mediscreen.WebApp.model.PatientBean;
import com.mediscreen.WebApp.proxy.MicroservicePatientProxy;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class PatientControllerTest {

    @Mock
    private MicroservicePatientProxy patientProxy;

    @InjectMocks
    private PatientController patientController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(patientController).build();
    }

    @Test
    public void getAllPatient_shouldReturnPatientListPage() throws Exception {
        // Given
        List<PatientBean> patientList = Arrays.asList(new PatientBean(), new PatientBean());
        given(patientProxy.getAllPatient()).willReturn(patientList);

        // When and Then
        mockMvc.perform(get("/patients/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("patients/list"))
                .andExpect(model().attribute("patients", patientList));
    }

    @Test
    public void addPatientForm_shouldReturnAddPatientPage() throws Exception {
        // When and Then
        mockMvc.perform(get("/patients/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("patients/add"))
                .andExpect(model().attributeExists("patient"));
    }

    @Test
    public void validate_shouldAddNewPatientAndRedirectToPatientList() throws Exception {
        // Given
        PatientBean patient = new PatientBean();
        when(patientProxy.addPatient(patient)).thenReturn(patient);
        when(patientProxy.getAllPatient()).thenReturn(Arrays.asList(patient));

        // When and Then
        mockMvc.perform(post("/patients/validate")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("firstName", "John")
                        .param("surname", "Doe")
                        .param("dateOfBirthday", "1985-06-01")
                        .param("gender", "M")
                        .param("phoneNumber", "123-456-7890")
                        .param("address", "123 Main St"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patients/list"))
                .andExpect(model().attributeExists("patients"));

        verify(patientProxy, times(1)).addPatient(any(PatientBean.class));
        verify(patientProxy, times(1)).getAllPatient();
    }

    @Test
    public void showUpdateForm_shouldReturnUpdatePatientPage() throws Exception {
        // Given
        Integer id = 1;
        PatientBean patient = new PatientBean();
        when(patientProxy.getPatientById(id)).thenReturn(patient);

        // When and Then
        mockMvc.perform(get("/patients/update/{id}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("patients/update"))
                .andExpect(model().attribute("patient", patient));
    }

    @Test
    public void updatePatient_shouldUpdatePatientAndRedirectToPatientList() throws Exception {
        // Given
        Integer id = 1;
        PatientBean patient = new PatientBean();
        when(patientProxy.updatePatient(patient)).thenReturn(patient);
        when(patientProxy.getAllPatient()).thenReturn(Arrays.asList(patient));

        // When and Then
        mockMvc.perform(post("/patients/update/{id}", id)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("firstName", "John")
                        .param("surname", "Doe")
                        .param("dob", "1985-06-01")
                        .param("dateOfBirthday", "1985-06-01")
                        .param("gender", "M")
                        .param("phoneNumber", "123-456-7890")
                        .param("address", "123 Main St"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patients/list"))
                .andExpect(model().attributeExists("patients"));

        verify(patientProxy, times(1)).updatePatient(any(PatientBean.class));
        verify(patientProxy, times(1)).getAllPatient();
    }

    @Test
    public void deletePatient_shouldDeletePatientAndRedirectToPatientList() throws Exception {
        // Given
        Integer id = 1;
        doNothing().when(patientProxy).deletePatient(id);

        // When and Then
        mockMvc.perform(get("/patients/delete/{id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/patients/list"))
                .andExpect(redirectedUrl("/patients/list"))
                .andExpect(model().attributeExists("patients"));

        verify(patientProxy, times(1)).deletePatient(id);
        verify(patientProxy, times(1)).getAllPatient();
    }
}

