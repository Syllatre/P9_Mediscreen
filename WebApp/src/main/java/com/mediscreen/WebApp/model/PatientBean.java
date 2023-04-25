package com.mediscreen.WebApp.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PatientBean {

        private Integer idPatient;

        @NotEmpty(message = "you must enter your first name")
        private String firstName;
        @NotEmpty(message = "you must enter your surname")
        private String surname;
        @NotNull
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        @Past(message = "You can't choose date after today")
        private LocalDate dateOfBirthday;
        @Pattern(regexp = "[MF]",message = "Select M or F")
        private String gender;

        private String phoneNumber;

        private String address;

        public PatientBean(String firstName, String surname, LocalDate dateOfBirthday, String gender, String phoneNumber, String address) {
            this.firstName = firstName;
            this.surname = surname;
            this.dateOfBirthday = dateOfBirthday;
            this.gender = gender;
            this.phoneNumber = phoneNumber;
            this.address = address;
        }
}
