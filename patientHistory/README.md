Sure, here's the README for Patient History Service:

# Patient History Service

This microservice is responsible for managing patient history information. It exposes a REST API that allows the user to:

- Add a new patient history note
- Retrieve a list of all patient history notes
- Retrieve a patient history note by ID
- Update a patient history note's information
- Delete a patient history note by ID

## Technologies Used

- Java 11
- Spring Boot
- Maven
- Swagger UI
- Lombok

## How to Run the Application

1. Clone the repository using the command `git clone https://github.com/<username>/<repository-name>.git`.
2. Navigate to the `patient-history` directory using the command `cd patient-history`.
3. Build the application using the command `mvn clean install`.
4. Run the application using the command `java -jar target/patient-history-0.0.1-SNAPSHOT.jar`.
5. Open a web browser and navigate to `http://localhost:8082/swagger-ui.html` to access the Swagger UI.

## REST API Endpoints

The following table summarizes the REST API endpoints exposed by the Patient History Service.

| HTTP Method | Path                             | Description                            |
|-------------|----------------------------------|----------------------------------------|
| GET         | /pathistory/list/{patId}         | Retrieve a list of all patient history notes for a given patient ID        |
| GET         | /pathistory/list                 | Retrieve a list of all patient history notes            |
| GET         | /pathistory/NoteById/{id}        | Retrieve a patient history note by ID               |
| POST        | /pathistory/add                  | Add a new patient history note                      |
| PUT         | /pathistory/update               | Update a patient history note's information         |
| DELETE      | /pathistory/delete/{id}          | Delete a patient history note by ID         |

## Swagger UI

The Patient History Service includes a Swagger UI that can be accessed at `http://localhost:8082/swagger-ui.html`. The Swagger UI provides a user-friendly interface for testing the REST API endpoints.