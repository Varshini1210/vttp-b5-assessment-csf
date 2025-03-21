FROM node:23 AS builder

WORKDIR /app

#Install Angular CLI
RUN npm i -g @angular/cli

COPY client/src src
COPY client/*json .

# Install dependencies, clean install
# Build the Angular Application

RUN npm ci && ng build

# stage 2
FROM eclipse-temurin:23-jdk AS builder2

LABEL maintainer="varsh1210"

WORKDIR /app

# Copy 
COPY server/pom.xml .
COPY server/mvnw .
COPY server/mvnw.cmd .
COPY server/src src
COPY server/.mvn .mvn

COPY --from=builder /app/dist/client/browser src/main/resources/static/

RUN chmod a+x ./mvnw && ./mvnw clean package -Dmaven.test.skip=true
 
#stage 3
FROM eclipse-temurin:23-jdk
# FROM eclipse-temurin:23-jre  -> don't have compiler, don't need compiler in final stage

WORKDIR /app

COPY --from=builder2 /app/target/server-0.0.1-SNAPSHOT.jar csf-assessment.jar

ENV PORT=8080

EXPOSE ${PORT}

ENTRYPOINT ["java","-jar","csf-assessment.jar"]