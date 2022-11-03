FROM adoptopenjdk/openjdk8
EXPOSE 8082
ADD /target/policy-microservice-0.0.1-SNAPSHOT.jar policy-microservice.jar
ENTRYPOINT ["java", "-jar","/policy-microservice.jar"]