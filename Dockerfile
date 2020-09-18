FROM node:12-slim as nodebuild

WORKDIR /tacs
COPY ./tacs .

RUN npm install
RUN npm run build

FROM maven:3-openjdk-11-slim as javabuild

WORKDIR /tacs
COPY ./tacs.api .

RUN mvn compile

RUN mkdir ./target/classes/public
COPY --from=nodebuild /tacs/build ./target/classes/public

RUN mvn package

FROM openjdk:11-jre

WORKDIR /tacs

COPY --from=javabuild /tacs/target/tacs.api-0.0.1-SNAPSHOT.jar tacs.api.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "tacs.api.jar"]
