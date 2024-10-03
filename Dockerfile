FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /home/gradle/project

COPY gradlew ./
COPY gradle ./gradle
COPY build.gradle ./
COPY settings.gradle ./

COPY src ./src

RUN ./gradlew --no-daemon clean bootJar --info

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

RUN addgroup -S appgroup && adduser -S appuser -G appgroup

COPY --from=build /home/gradle/project/build/libs/*.jar app.jar

RUN chown appuser:appgroup app.jar
USER appuser

EXPOSE 8001

ENTRYPOINT ["java", "-Xms1024m", "-Xmx1024m", "-XX:+UseZGC", "-XX:+ZGenerational", "-Xlog:gc", "-jar","app.jar"]