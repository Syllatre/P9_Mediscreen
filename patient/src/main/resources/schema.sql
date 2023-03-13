
CREATE SCHEMA IF NOT EXISTS `patient`;
USE `patient`;


CREATE TABLE IF NOT EXISTS `patient`.`patient` (
  `idpatient` int NOT NULL AUTO_INCREMENT,
  `prenom` varchar(45) NOT NULL,
  `nom` varchar(45) NOT NULL,
  `date_naissance` date NOT NULL,
  `genre` varchar(1) NOT NULL,
  `numero_telephone` varchar(20) DEFAULT NULL,
  `adresse` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`idpatient`),
  UNIQUE KEY `idpatient_UNIQUE` (`idpatient`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;