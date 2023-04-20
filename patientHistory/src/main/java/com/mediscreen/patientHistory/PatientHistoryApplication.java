package com.mediscreen.patientHistory;

import com.mediscreen.patientHistory.model.PatientHistory;
import com.mediscreen.patientHistory.service.PatientHistoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableMongoRepositories
public class PatientHistoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatientHistoryApplication.class, args);
    }

    @Bean
    CommandLineRunner init(PatientHistoryService patientHistoryService) {
        return args -> {


            List<PatientHistory> patientHistories = Arrays.asList(
                    new PatientHistory(null, 1, "Le patient déclare qu'il « se sent très bien »\n" +
                            "Poids égal ou inférieur au poids recommandé\n", LocalDate.now()),
                    new PatientHistory(null, 1, "Le patient déclare qu'il se sent fatigué pendant la journée\n" +
                            "Il se plaint également de douleurs musculaires\n" +
                            "Tests de laboratoire indiquant une microalbumine élevée\n", LocalDate.now()),
                    new PatientHistory(null, 1, "Le patient déclare qu'il ne se sent pas si fatigué que ça\n" +
                            "Fumeur, il a arrêté dans les 12 mois précédents\n" +
                            "Tests de laboratoire indiquant que les anticorps sont élevés", LocalDate.now()),
                    new PatientHistory(null, 2, "Le patient déclare qu'il ressent beaucoup de stress au travail\n" +
                            "Il se plaint également que son audition est anormale dernièrement\n", LocalDate.now()),
                    new PatientHistory(null, 2, "Le patient déclare avoir fait une réaction aux médicaments au cours des 3 derniers mois\n" +
                            "Il remarque également que son audition continue d'être anormale\n", LocalDate.now()),
                    new PatientHistory(null, 2, "Tests de laboratoire indiquant une microalbumine élevée", LocalDate.now()),
                    new PatientHistory(null, 2, "Le patient déclare que tout semble aller bien\n" +
                            "Le laboratoire rapporte que l'hémoglobine A1C dépasse le niveau recommandé\n" +
                            "Le patient déclare qu’il fume depuis longtemps", LocalDate.now()),
                    new PatientHistory(null, 3, "Le patient déclare qu'il fume depuis peu", LocalDate.now()),
                    new PatientHistory(null, 3, "Le patient déclare qu'il est fumeur et qu'il a cessé de fumer l'année dernière\n" +
                            "Il se plaint également de crises d’apnée respiratoire anormales\n" +
                            "Tests de laboratoire indiquant un taux de cholestérol LDL élevé\n", LocalDate.now()),
                    new PatientHistory(null, 3, "Tests de laboratoire indiquant une microalbumine élevée", LocalDate.now()),
                    new PatientHistory(null, 3, "Tests de laboratoire indiquant un taux de cholestérol LDL élevé", LocalDate.now()),
                    new PatientHistory(null, 4, "Le patient déclare qu'il lui est devenu difficile de monter les escaliers\n" +
                            "Il se plaint également d’être essoufflé\n" +
                            "Tests de laboratoire indiquant que les anticorps sont élevés\n" +
                            "Réaction aux médicaments", LocalDate.now()),
                    new PatientHistory(null, 4, "Le patient déclare qu'il a mal au dos lorsqu'il reste assis pendant longtemps", LocalDate.now()),
                    new PatientHistory(null, 4, "Le patient déclare avoir commencé à fumer depuis peu\n" +
                            "Hémoglobine A1C supérieure au niveau recommandé", LocalDate.now()),
                    new PatientHistory(null, 5, "Le patient remarque également que certains aliments ont un goût différent\n" +
                            "Réaction apparente aux médicaments\n" +
                            "Poids corporel supérieur au poids recommandé\n", LocalDate.now()),
                    new PatientHistory(null, 5, "Le patient déclare avoir eu plusieurs épisodes de vertige depuis la dernière visite.\n" +
                            "Taille incluse dans la fourchette concernée", LocalDate.now()),
                    new PatientHistory(null, 5, "Le patient déclare qu'il souffre encore de douleurs cervicales occasionnelles\n" +
                            "Tests de laboratoire indiquant une microalbumine élevée\n" +
                            "Fumeur, il a arrêté dans les 12 mois précédents\n", LocalDate.now()),
                    new PatientHistory(null, 5, "Le patient déclare avoir eu plusieurs épisodes de vertige depuis la dernière visite.\n" +
                            "Tests de laboratoire indiquant que les anticorps sont élevés\n", LocalDate.now()),
                    new PatientHistory(null, 6, "Le patient déclare qu'il se sent bien\n" +
                            "Poids corporel supérieur au poids recommandé", LocalDate.now()),
                    new PatientHistory(null, 6, "Le patient déclare qu'il se sent bien", LocalDate.now()),
                    new PatientHistory(null, 7, "Le patient déclare qu'il se réveille souvent avec une raideur articulaire\n" +
                            "Il se plaint également de difficultés pour s’endormir\n" +
                            "Poids corporel supérieur au poids recommandé\n" +
                            "Tests de laboratoire indiquant un taux de cholestérol LDL élevé", LocalDate.now()),
                    new PatientHistory(null, 8, "Les tests de laboratoire indiquent que les anticorps sont élevés\n" +
                            "Hémoglobine A1C supérieure au niveau recommandé\n", LocalDate.now()),
                    new PatientHistory(null, 9, "Le patient déclare avoir de la difficulté à se concentrer sur ses devoirs scolaires\n" +
                            "Hémoglobine A1C supérieure au niveau recommandé\n", LocalDate.now()),
                    new PatientHistory(null, 9, "Le patient déclare qu'il s’impatiente facilement en cas d’attente prolongée\n" +
                            "Il signale également que les produits du distributeur automatique ne sont pas bons\n" +
                            "Tests de laboratoire signalant des taux anormaux de cellules sanguines\n", LocalDate.now()),
                    new PatientHistory(null, 9, "Le patient signale qu'il est facilement irrité par des broutilles\n" +
                            "Il déclare également que l'aspirateur des voisins fait trop de bruit\n" +
                            "Tests de laboratoire indiquant que les anticorps sont élevés", LocalDate.now()),
                    new PatientHistory(null, 10, "Le patient déclare qu'il n'a aucun problème", LocalDate.now()),
                    new PatientHistory(null, 10, "Le patient déclare qu'il n'a aucun problème\n" +
                            "Taille incluse dans la fourchette concernée\n" +
                            "Hémoglobine A1C supérieure au niveau recommandé\n", LocalDate.now()),
                    new PatientHistory(null, 10, "Le patient déclare qu'il n'a aucun problème\n" +
                            "Poids corporel supérieur au poids recommandé\n" +
                            "Le patient a signalé plusieurs épisodes de vertige depuis sa dernière visite\n", LocalDate.now())
            );
            patientHistoryService.insertData(patientHistories);

            // Supprimer les données à l'arrêt de l'application
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                patientHistoryService.removeAllData();
            }));
        };
    }
}
