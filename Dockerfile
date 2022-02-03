FROM gradle:7.2 as gradle

WORKDIR /app
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY src ./src
RUN gradle --no-daemon bootJar

FROM adoptopenjdk/openjdk11

COPY --from=gradle /app/build/libs/tictactoe-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar"]