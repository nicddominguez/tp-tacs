FROM maven:3-openjdk-11-slim as javabuild

WORKDIR /tacs
COPY ./tacs.api .

RUN mvn package

FROM openjdk:11-jre

WORKDIR /tacs

COPY --from=javabuild /tacs/target/tacs.api-0.0.1-SNAPSHOT.jar tacs.api.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "tacs.api.jar"]
