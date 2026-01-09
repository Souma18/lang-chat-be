FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

COPY gradlew ./
COPY gradle ./gradle
COPY build.gradle settings.gradle ./
COPY src ./src

RUN ./gradlew clean bootJar --no-daemon

FROM eclipse-temurin:21-jdk AS run
WORKDIR /app

ENV SPRING_PROFILES_ACTIVE=prod

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/app.jar"]

