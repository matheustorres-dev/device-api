# Use an OpenJDK image
FROM openjdk:21-jdk-slim

# Set environment variables
ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS \
    JAVA_OPTS=""

# Set working directory
WORKDIR /app

# Copy Maven dependencies and build
COPY target/device-api.jar device-api.jar

# Run the application
ENTRYPOINT ["java", "-jar", "device-api.jar"]