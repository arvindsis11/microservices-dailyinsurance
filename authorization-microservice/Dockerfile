FROM adoptopenjdk/openjdk8
EXPOSE 8080
ADD /target/authorization-microservice-0.0.1-SNAPSHOT.jar authorization-microservice.jar
ENTRYPOINT ["java", "-jar","/authorization-microservice.jar"]