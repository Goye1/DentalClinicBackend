FROM amazoncorretto:20-alpine-jdk

ARG JAR_FILE=target/DentalClinicManagement-0.0.1-SNAPSHOT.jar

WORKDIR /app

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","app.jar", "com/DentalClinicX/DentalClinicManagement/ClinicManagementApplication"]

EXPOSE 8080