# Risk Service

The Risk Service is responsible for calculating the risk of diabetes for a patient. It exposes a REST API that allows the user to:

- Retrieve the risk of diabetes for a patient by patient ID

## Technologies Used

- Java 11
- Spring Boot
- Maven
- Swagger UI
- Lombok
- OpenFeign

## How to Run the Application

1. Clone the repository using the command `git clone https://github.com/<username>/<repository-name>.git`.
2. Navigate to the `risk` directory using the command `cd risk`.
3. Build the application using the command `mvn clean install`.
4. Run the application using the command `java -jar target/risk-0.0.1-SNAPSHOT.jar`.
5. Open a web browser and navigate to `http://localhost:8082/swagger-ui.html` to access the Swagger UI.

## REST API Endpoints

The following table summarizes the REST API endpoints exposed by the Risk Service.

| HTTP Method | Path                     | Description                                         |
|-------------|--------------------------|-----------------------------------------------------|
| GET         | /assess/risk/{idPatient} | Retrieve the risk of diabetes for a patient by ID   |

## Swagger UI

The Risk Service includes a Swagger UI that can be accessed at `http://localhost:8082/swagger-ui.html`. The Swagger UI provides a user-friendly interface for testing the REST API endpoints.

## Proxies

The Risk Service uses OpenFeign to make requests to the Patient and Patient History services. The following table summarizes the proxy interfaces used by the Risk Service.

| Service        | Proxy Interface Name         | Description                              |
|----------------|-------------------------------|------------------------------------------|
| Patient        | MicroservicePatientProxy      | Makes requests to the Patient Service    |
| Patient History| MicroservicePatientHistoryProxy| Makes requests to the Patient History Service |