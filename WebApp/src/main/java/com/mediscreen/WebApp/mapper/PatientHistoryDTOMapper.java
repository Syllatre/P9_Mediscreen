package com.mediscreen.WebApp.mapper;

import com.mediscreen.WebApp.model.DTO.PatientHistoryDTO;
import com.mediscreen.WebApp.model.PatientBean;
import com.mediscreen.WebApp.model.PatientHistoryBean;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PatientHistoryDTOMapper {
    PatientHistoryDTOMapper INSTANCE = Mappers.getMapper(PatientHistoryDTOMapper.class);

    @Mapping(source = "patient.idPatient", target = "idPatient")
    @Mapping(source = "patient.firstName", target = "firstName")
    @Mapping(source = "patient.surname", target = "surname")
    @Mapping(source = "patient.dateOfBirthday", target = "dateOfBirthday")
    @Mapping(source = "patient.gender", target = "gender")
    @Mapping(source = "patient.phoneNumber", target = "phoneNumber")
    @Mapping(source = "patient.address", target = "address")
    @Mapping(source = "note.date", target = "noteDate")
    @Mapping(source = "note.note", target = "noteText")
    @Mapping(source = "note.id", target = "id")
    PatientHistoryDTO from(PatientBean patient,PatientHistoryBean note);
}
