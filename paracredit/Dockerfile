# Use a base image that supports Linux amd64 and has Java 17 installed
FROM azul/zulu-openjdk:17-latest
# Set the working directory in the container
WORKDIR /app

# Copy the executable jar file from your host into the container
COPY target/*.jar app.jar

# Expose any necessary ports
# EXPOSE 8080

# Specify the command to run on container start
ENTRYPOINT ["java", "-jar", "app.jar"]

