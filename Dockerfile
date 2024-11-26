FROM openjdk:17-jdk


ARG JAR_FILE=build/libs/*.jar
ARG PROFILES
ARG ENV



COPY ${JAR_FILE} app.jar

# ENTRYPOINT 정의
ENTRYPOINT ["java", "-Dspring.profiles.active=${PROFILES}", "-Dserver.env=${ENV}", "-jar", "app.jar"]
