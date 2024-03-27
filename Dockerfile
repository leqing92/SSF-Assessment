#build container
FROM openjdk:21-jdk-bullseye AS builder

#Create a directory for our application
WORKDIR /app

COPY mvnw .
COPY pom.xml .
COPY .mvn .mvn
COPY src src

# window need the follow to make it executable
RUN chmod a+x mvnw
RUN ./mvnw package -Dmaven.test.skip=true

#after this line is another container; everything for/run on this container
#run container
FROM openjdk:21-jdk-bullseye

WORKDIR /app_run

COPY --from=builder /app/target/ibf-b4-ssf-assessment-0.0.1-SNAPSHOT.jar app.jar
COPY --from=builder /app/src/main/resources/static/movies.json /app_run/src/main/resources/static/movies.json

ENV PORT=8080 

EXPOSE ${PORT}

ENTRYPOINT SERVER_PORT=${PORT} java -jar app.jar