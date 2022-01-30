FROM openjdk:11
ARG JAR_FILE=target/credit-system-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} creditsystem.jar
EXPOSE 8095
ENTRYPOINT ["java", "-jar", "creditsystem.jar"]