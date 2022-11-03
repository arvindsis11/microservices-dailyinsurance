# Daily Insurance Portal

Full stack application using angular(frontend) and spring boot rest api

## Installation
### step 1: Run all microservices.
1. run all microservices in sts or eclipse
```java
1: Authorization-microservice> http://localhost:8080/swagger-ui.html
2: Wallet-microservice> http://localhost:8081/swagger-ui.html
3: Policy-microservice> http://localhost:8082/swagger-ui.htm
4: Processclaim-microservice> http://localhost:8083/swagger-ui.htm
```
2. H2 In memory database used for development.
```java
1: Authorization-microservice> http://localhost:8080/h2-console
2: Wallet-microservice> http://localhost:8081/h2-console
3: Policy-microservice> http://localhost:8082/h2-console
4: Processclaim-microservice> http://localhost:8083/h2-console
#username - user
#password - 
```

##step 2: run frontend using following commands.


```bash
npm install
ng serve -o
```
This will open portal on:
```java
http://localhost:4200/login
```


## For mysql configuration

#### step-1:Add following dependency to pom.xml

```xml
<!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.28</version>
</dependency>

```

### step-2: To create a new database, run the following commands at the mysql prompt:
```sql
mysql> create database db_example; -- Creates the new database
mysql> create user 'springuser'@'%' identified by 'ThePassword'; -- Creates the user
mysql> grant all on db_example.* to 'springuser'@'%'; -- Gives all privileges to the new user on the newly created database
```
### step-3: Change application.properties 
```properties
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url = jdbc:mysql://localhost:3306/yourdb
spring.datasource.username = root
spring.datasource.password = Password
spring.jpa.show-sql = true
spring.jpa.hibernate.ddl-auto = update
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL5Dialect
server.port=9191
```

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.
https://github.com/Java-Techie-jt/spring-boot-crud-example
```xml
<dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>
```
```shell
docker build -t arvindsis35/policy-microservice.jar:latest -f Dockerfile .
docker push arvindsis35/policy-microservice.jar
docker run -p 8080:8080 arvindsis35/policy-microservice.jar
```
## License
[Github](https://github.com/arvindsis11)
