## Overview

The **Stock Manager API** is a Java-based application designed to manage and process stock data efficiently. Built using **Spring Boot**, it provides a robust backend for handling stock-related operations such as retrieving stock prices, caching results, and sorting stocks based on various criteria. 

### Key Features:
- **Dynamic Configuration**: Interfaces and strategies are dynamically initialized based on user-defined properties.
- **Caching Mechanism**: Implements caching strategies to optimize API calls and reduce latency.
- **Concurrent Batch Processing**: Splits stock data into manageable batches for efficient processing using multithreading.
- **RESTful API**: Exposes endpoints for interacting with the stock data.
- **Docker Support**: Easily deployable as a containerized application. 
- **API** is live at: https://stock-manager.reaz-projects.uk/health

# Prerequisite: 
- Java 21  
- Maven

# How to run the program
- to clean install/compile: `mvn clean install`
- to run the api locally: `mvn spring-boot:run`

## To package a .jar

- to package the code: `mvn clean package`
- to run the packaged jar (from root dir): `java -jar target/stock_manager-0.0.1-SNAPSHOT.jar` 

## To run a container on public internet
- build the docker image from root dir: `docker build -t stock-manager-api .` 
- to run the container: `docker run -d -p <target_port>:8080 stock-manager-api`

### Optional docker commands 
- to verify docker daemon is running: `sudo docker ps`  
- verify local host is running ok: `curl http://localhost:8080/health`