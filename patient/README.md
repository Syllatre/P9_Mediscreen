# Patient Service

This microservice is responsible for managing patient information. It exposes a REST API that allows the user to:

- Add a new patient
- Retrieve a list of all patients
- Retrieve a patient by ID
- Update a patient's information
- Delete a patient by ID

## Technologies Used

- Java 11
- Spring Boot
- Maven
- Swagger UI
- Lombok

## How to Run the Application

1. Clone the repository using the command `git clone https://github.com/<username>/<repository-name>.git`.
2. Navigate to the `patient` directory using the command `cd patient`.
3. Build the application using the command `mvn clean install`.
4. Run the application using the command `java -jar target/patient-0.0.1-SNAPSHOT.jar`.
5. Open a web browser and navigate to `http://localhost:8081/swagger-ui.html` to access the Swagger UI.

## REST API Endpoints

The following table summarizes the REST API endpoints exposed by the Patient Service.

| HTTP Method | Path                   | Description                            |
|-------------|------------------------|----------------------------------------|
| GET         | /patients/list         | Retrieve a list of all patients        |
| GET         | /patients/{idPatient}  | Retrieve a patient by ID               |
| POST        | /patients/add          | Add a new patient                      |
| PUT         | /patients/update       | Update a patient's information         |
| DELETE      | /patients/delete/{idPatient} | Delete a patient by ID          |

## Swagger UI

The Patient Service includes a Swagger UI that can be accessed at `http://localhost:8081/swagger-ui.html`. The Swagger UI provides a user-friendly interface for testing the REST API endpoints.