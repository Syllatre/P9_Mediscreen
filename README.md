# P9_Mediscreen
Mediscreen is an application designed to help doctors detect diabetes. 
It relies on doctors' notes and scans keywords to return a diabetes risk assessment.

### 1 Prérequis
![Java Version](https://img.shields.io/badge/Java-11-red)
![Maven Version](https://img.shields.io/badge/Maven-3.8.x-blue)
![Docker-Compose](https://img.shields.io/badge/Docker_compose-3.x-cyan)

### 2 Launch the application
- Clone the project
- Navigate to the root of the my-parent-project folder and type ``mvn package`` to package all microservices
- Return to the main folder and type ``docker-compose up -d --build``
- Go to the URL http://localhost:8080/patients/list

### 3 Documentations
Below are the documentation URLs for each API:
- patient: http://localhost:8081/swagger-ui/index.html
- patientHistory: http://localhost:8082/swagger-ui/index.html
- risk: http://localhost:8083/swagger-ui/index.html