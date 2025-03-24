# device-api

Device Resources API

## Run

To run this project you are going to need:

- [JDK 21](https://www.oracle.com/br/java/technologies/downloads/)
- [Maven 3](https://maven.apache.org)
- [Docker](https://docs.docker.com/desktop/)

 Execute the following commands to package the project and compose the Docker container:

```shell
mvn clean package
```

```shell
docker-compose up --build
```

## Rules

This application is an API that allows managing of Devices.
It is possible to create, update, delete, and search for devices.

It is also possible to access API documentation when the project is running, by accessing the [OpenAPI local](http://localhost:8080/swagger-ui/index.html) address.


## Endpoint

You can access the API: http://localhost:8080/devices/v1/


## Code Coverage

This project has Test Coverage through JaCoCo, the report is available on folder ./target after running install:

```shell
mvn clean install
```