# Step 1: Build karne ke liye Maven image use karo
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn -B dependency:go-offline
RUN mvn -B clean package -DskipTests

# Step 2: Run karne ke liye lightweight Java image use karo
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
