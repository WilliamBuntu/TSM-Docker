FROM eclipse-temurin:24-jdk as build
WORKDIR /app

# Copy maven executable and pom.xml
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Make the mvnw script executable
RUN chmod +x ./mvnw

# Build all dependencies for offline use
RUN ./mvnw dependency:go-offline -B


# Copy the project source
COPY src src

# Package the application
RUN ./mvnw package -DskipTests

# Runtime stage
FROM eclipse-temurin:24-jre
WORKDIR /app

# Copy the jar file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]