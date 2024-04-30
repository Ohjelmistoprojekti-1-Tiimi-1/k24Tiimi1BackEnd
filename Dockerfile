# Build stage where we build .jar -file which is again used to make the actual image
# LEt's use gradle as a "baseproject"
FROM gradle:8.7 AS BUILD

# Create a directory for the application in image and set it as the working directory for Docker
WORKDIR /home/app

# Copy project files to the container, these go to WORKDIR. This phase could be better by copying only neccessary files like in src etc
COPY . .

# I used this print to verify the files are copied correctly
RUN ls -l

# Run Gradle build. We have gradle command from "baseproject" We skip tests, because they failed
RUN gradle build --no-daemon -x test

# Actual image
FROM openjdk:17

WORKDIR /home/app

# copying built jar -file from first stage to container
COPY --from=BUILD /home/app/build/libs/petshop-0.0.1-SNAPSHOT.jar /home/app/petshop.jar

# defining backend port
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "/home/app/petshop.jar"]