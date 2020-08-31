FROM openjdk:11 as build

RUN apt update
RUN apt install -y maven

RUN mkdir /tacs
COPY ./tacs.api /tacs/

RUN mvn -f /tacs/pom.xml clean package


FROM openjdk:11-jre

RUN mkdir /tacs
COPY --from=build /tacs/target/tacs.api-0.0.1-SNAPSHOT.jar /tacs/tacs.api.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/tacs/tacs.api.jar"]