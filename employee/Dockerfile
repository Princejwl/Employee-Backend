
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

# Use OpenJDK 17 base image
FROM openjdk:17-jdk-slim

# Set working directory inside container
WORKDIR /app

# Copy pom.xml and source code
COPY pom.xml .
COPY src ./src

# Install Maven
RUN apt-get update && apt-get install -y maven

# Download dependencies
RUN mvn dependency:go-offline -B

# Build the project (skip tests)
RUN mvn clean package -DskipTests

# Run the generated jar file
CMD ["java", "-jar", "target/employee-0.0.1-SNAPSHOT.jar"]

