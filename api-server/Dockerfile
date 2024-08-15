# Build stage
FROM gradle:7.4.2-jdk17 as build

# Set working directory
WORKDIR /app

# Copy project files
COPY . .

# Build the application
RUN ./gradlew build

# Package stage
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose the port
EXPOSE 8080

# Set Profile
ENV SPRING_PROFILES_ACTIVE=prod

# Run the application
CMD ["java", "-jar", "app.jar"]