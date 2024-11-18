# Step 1: Use an OpenJDK image for building the Spring Boot application
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file from your local machine into the container
COPY target/BankingApplication-0.0.1-SNAPSHOT.jar /app/banking-app.jar

# Expose port for the Spring Boot app
EXPOSE 8089

# Start the Spring Boot application
CMD ["java", "-jar", "/app/banking-app.jar"]
