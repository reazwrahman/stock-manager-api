# How to run the program

- to clean install/compile: `mvn clean install`
- to run the api locally: `mvn spring-boot:run`

## To package a .jar

- to package the code: `mvn clean package`
- to run the packaged jar (from root dir): `java -jar target/stock_manager-0.0.1-SNAPSHOT.jar` 

## To run a container
- build the docker image from root dir: `docker build -t stock-manager-api .` 
- to run the container: `docker run -p <desired port no>:8080 stock-manager-api`