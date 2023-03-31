package com.mediscreen.patientHistory;

import com.mediscreen.patientHistory.model.PatientHistory;
import com.mediscreen.patientHistory.repository.PatientHistoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
@EnableMongoRepositories
public class PatientHistoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatientHistoryApplication.class, args);
	}

	@Bean
	CommandLineRunner init(PatientHistoryRepository patientHistoryRepository) {
		return args -> {
			PatientHistory patientHistory1 = new PatientHistory(null, 1, "Patient states that they are 'feeling terrific' Weight at or below recommended level", LocalDate.now());
			PatientHistory patientHistory2 = new PatientHistory(null, 2, "Patient states that they are feeling a great deal of stress at work Patient also complains that their hearing seems Abnormal as of late", LocalDate.now());
			PatientHistory patientHistory3 = new PatientHistory(null, 2, "Patient states that they have had a Reaction to medication within last 3 months Patient also complains that their hearing continues to be problematic", LocalDate.now());
			PatientHistory patientHistory4 = new PatientHistory(null, 3, "Patient states that they are short term Smoker", LocalDate.now());
			PatientHistory patientHistory5 = new PatientHistory(null, 3, "Patient states that they quit within last year Patient also complains that of Abnormal breathing spells Lab reports Cholesterol LDL high", LocalDate.now());
			PatientHistory patientHistory6 = new PatientHistory(null, 4, "Patient states that walking up stairs has become difficult Patient also complains that they are having shortness of breath Lab results indicate Antibodies present elevated Reaction to medication", LocalDate.now());
			PatientHistory patientHistory7 = new PatientHistory(null, 4, "Patient states that they are experiencing back pain when seated for a long time", LocalDate.now());
			PatientHistory patientHistory8 = new PatientHistory(null, 4, "Patient states that they are a short term Smoker Hemoglobin A1C above recommended level", LocalDate.now());
			PatientHistory patientHistory9 = new PatientHistory(null, 4, "Patient states that Body Height, Body Weight, Cholesterol, Dizziness and Reaction", LocalDate.now());
			patientHistoryRepository.saveAll(List.of(patientHistory1, patientHistory2, patientHistory3,patientHistory5,patientHistory6,patientHistory7,patientHistory8,patientHistory9));
		};
	}
}
