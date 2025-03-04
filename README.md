# Spring-app-to-Trigger-awx-job_templates

This Spring Boot application is designed to trigger AWX job templates using the AWX API. It provides REST endpoints to launch job templates and retrieve job results.

## Prerequisites

- Java 11 or higher
- Maven 3.6.3 or higher
- AWX server with API access
- Git

## Getting Started

### Clone the Repository

Clone the repository to your local machine using Git.

```bash
git clone https://github.com/Srigopinath-A/Spring-app-to-Trigger-awx-job_templates.git
cd Spring-app-to-Trigger-awx-job_templates
```

### Build the Project

Use Maven to build the project.

```bash
mvn clean install
```

### Configuration

Create an `application.properties` file in the `src/main/resources` directory with the following content:

```properties
awx.url=http://your-awx-server
awx.token=your-awx-token
```

Replace `http://your-awx-server` with the URL of your AWX server and `your-awx-token` with your AWX API token.

### Running the Application

Run the Spring Boot application using Maven.

```bash
mvn spring-boot:run
```

### Accessing the Application

Once the application is running, you can access it at `http://localhost:8080`.

## API Endpoints

### Trigger AWX Job

To trigger an AWX job template, use the following endpoint:

- **URL**: `/api/awx/trigger-job/{jobTemplateId}`
- **Method**: `POST`
- **Path Variable**: `jobTemplateId` - ID of the job template to trigger

**Example Request**:

```bash
curl -X POST "http://localhost:8080/api/awx/trigger-job/1"
```

**Example Response**:

```json
{
  "id": 123,
  "name": "Example Job",
  "status": "pending"
}
```

### Get Job Result

To get the result of an AWX job, use the following endpoint:

- **URL**: `/api/awx/job-result/{jobId}`
- **Method**: `GET`
- **Path Variable**: `jobId` - ID of the job to retrieve the result for

**Example Request**:

```bash
curl -X GET "http://localhost:8080/api/awx/job-result/123"
```

**Example Response**:

```json
{
  "id": 123,
  "name": "Example Job",
  "status": "successful",
  "result": "Job completed successfully."
}
```

## Project Structure

```
Spring-app-to-Trigger-awx-job_templates
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── example
│   │   │           └── ping
│   │   │               ├── PingApplication.java
│   │   │               ├── config
│   │   │               │   └── AppConfig.java
│   │   │               ├── controller
│   │   │               │   └── AWXController.java
│   │   │               └── service
│   │   │                   └── AWXService.java
│   │   └── resources
│   │       ├── application.properties
│   │       └── templates
│   │           └── index.html
├── .gitignore
├── HELP.md
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

## Contributing

Contributions are welcome! Please open an issue or submit a pull request for any changes.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
