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