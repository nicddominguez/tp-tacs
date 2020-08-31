FROM openjdk:11

RUN apt update
RUN apt install -y maven

RUN mkdir tacs
COPY ./tacs.api /tacs/

RUN mvn -f /tacs/pom.xml clean package

ENTRYPOINT ["java", "-jar", "/tacs/target/tacs.api-0.0.1-SNAPSHOT.jar"]