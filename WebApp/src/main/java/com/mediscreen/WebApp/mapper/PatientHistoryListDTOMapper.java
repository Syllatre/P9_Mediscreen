package com.mediscreen.WebApp.mapper;

import com.mediscreen.WebApp.model.DTO.PatientHistoryListDTO;
import com.mediscreen.WebApp.model.PatientBean;
import com.mediscreen.WebApp.model.PatientHistoryBean;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PatientHistoryListDTOMapper {

    PatientHistoryListDTOMapper INSTANCE = Mappers.getMapper(PatientHistoryListDTOMapper.class);

    @Mapping(source = "patient.idPatient", target = "idPatient")
    @Mapping(source = "patient.firstName", target = "firstName")
    @Mapping(source = "patient.surname", target = "surname")
    @Mapping(source = "patient.dateOfBirthday", target = "dateOfBirthday")
    @Mapping(source = "patient.gender", target = "gender")
    @Mapping(source = "note", target = "noteList")
    PatientHistoryListDTO from(PatientBean patient, List<PatientHistoryBean> note);
}
