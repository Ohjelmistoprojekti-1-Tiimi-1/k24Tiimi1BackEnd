

# Here is the most simple way of making the image of the app.
# run:
# ./gradlew build
# docker build -t  petshop .
# docker run -it petshop:0.0.1-SNAPSHOT
# FROM openjdk:17
# EXPOSE 8080
# RUN mkdir /app
# COPY build/libs/petshop-0.0.1-SNAPSHOT.jar /app/petshop.jar
# ENTRYPOINT ["java","-jar","/app/petshop.jar"]


#Better way would be to make it 2-phased and build .jar file with gradle while building the image
#Something like:

#
# Gradle Build
# using official gradle example image as the base for petshop image
FROM openjdk:17

WORKDIR /home/app

CMD ["./gradlew", "clean", "bootJar"]

COPY build/libs/petshop-0.0.1-SNAPSHOT.jar app.jar
# Copy project files and Gradle wrapper script to the container
EXPOSE 8080
# Setting working directory to /home/app where we copied our files. Now we dont have to define it every time
ENTRYPOINT ["java", "-jar","./app.jar"]