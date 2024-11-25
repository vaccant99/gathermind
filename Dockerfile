FROM amazoncorretto:11-alpine-jdk


ARG JAR_FILE=target/*.jar
ARG PROFILES
ARG ENV


ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

# ENTRYPOINT 정의
ENTRYPOINT ["java", "-Dspring.profiles.active=${PROFILES}", "-Dserver.env=${ENV}", "-jar", "app.jar"]
