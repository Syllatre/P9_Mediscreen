# P9_Mediscreen
Mediscreen est une application qui a pour but d'aider les médecins à détecter les diabètes. Elle se base sur les notes du medecin
et scan les mots clé pour retourner une évaluation du risque de diabète.

### 1 Prérequis
![Java Version](https://img.shields.io/badge/Java-11-red)
![Maven Version](https://img.shields.io/badge/Maven-3.8.x-blue)
![Docker-Compose](https://img.shields.io/badge/Docker_compose-3.x-cyan)
![MySQL Version](https://img.shields.io/badge/MySQL-8-green)
![MongoDB Version](https://img.shields.io/badge/MongoDB-5-green)

### 2 Lancer l'application
- cloner le projet
- taper docker-compose up -d --build
- rendez-vous sur l'url http://localhost:8080/patients/list

### 3 Documentations
Vous trouverez ci-dessous les urls des documentations pour chaque API:
- patient: http://localhost:8081/swagger-ui/index.html
- patientHistory: http://localhost:8082/swagger-ui/index.html
- risk: http://localhost:8083/swagger-ui/index.html