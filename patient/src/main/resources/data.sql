TRUNCATE TABLE `patient`;


INSERT INTO `patient`(idpatient, prenom, nom, date_naissance, genre, numero_telephone, adresse) values
(1,'Test', 'TestNone', '1966-12-31', 'F','100-222-3333', '1 Brookside St'),
(2,'Test', 'TestBorderline', '1945-06-24', 'M','200-333-4444', '2 High St&phone'),
(3,'Test', 'TestInDanger', '2004-06-18', 'M','300-444-5555', '3 Club Road&phone'),
(4,'Test', 'TestEarlyOnset', '2002-06-28', 'F','400-555-6666', '4 Valley Dr&phone');