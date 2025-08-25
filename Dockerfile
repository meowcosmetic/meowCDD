FROM eclipse-temurin:17-jdk-alpine

# Install necessary packages
RUN apk add --no-cache curl

# Create application user
RUN addgroup -S spring && adduser -S spring -G spring

# Set working directory
WORKDIR /app

# Copy the JAR file
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Change ownership to spring user
RUN chown spring:spring /app/app.jar

# Switch to spring user
USER spring:spring

# Expose port
EXPOSE 8101

# Set JVM options for better performance and database connectivity
ENV JAVA_OPTS="-Xmx512m -Xms256m -Djava.security.egd=file:/dev/./urandom"

# Run the application with Docker profile
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar --spring.profiles.active=docker"]
