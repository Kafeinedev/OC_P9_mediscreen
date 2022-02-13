# Mediscreen

Ensemble of services destined to help the diagnostic of diabetes.

## Getting Started

### Dependencies

-Java 11\
-Maven 3.8.4\
-Docker 20.10.12\
-Docker-compose

### Installing

-Clone the github repository at address https://github.com/Kafeinedev/OC_P9_mediscreen

-Position yourself at the root of the project and run the following commands :

Build PatientInfo microservice with :
```
mvn -f PatientInfo clean package -DskipTests
```
Build Note microservice with :
```
mvn -f Notes clean package -DskipTests
```
We skip the tests on those one because they require a database connection.

Build assessment microservice with :
```
mvn -f Assessment clean package
```
Build the clientui application with :
```
mvn -f ClientUi clean package
```
Build the patientInfo docker image with :
```
docker build -t patientinfo PatientInfo
```
Build the note docker image with :
```
docker build -t note Notes
```
Build the assessment docker image with :
```
docker build -t assessment Assessment
```

Pull database docker image with :
```
docker pull mariadb
```
```
docker pull mongo
```
Build the client ui docker image with :
```
docker build -t mediscreenui ClientUi
```

### Executing program

Inside projectroot run the command
```
docker-compose up -d
```

You can access the application ui using a browser at the address http://localhost:8080/patient/list
