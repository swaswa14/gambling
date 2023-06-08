#FROM eclipse-temurin:17-jdk-focal
#WORKDIR /app
#
#ARG JAR_FILE=target/back-end-back-end-0.1.0.jar
#
#COPY ${JAR_FILE} backend.jar
#ENTRYPOINT ["java","-Dspring.profiles.active=dev","-jar","/backend.jar"]
#EXPOSE 8080

FROM eclipse-temurin:17-jdk-focal

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

COPY src ./src

# Set the default profile
ENV SPRING_PROFILES_ACTIVE=test

CMD ["./mvnw", "spring-boot:run", "-Dspring-boot.run.profiles=${SPRING_PROFILES_ACTIVE}"]

