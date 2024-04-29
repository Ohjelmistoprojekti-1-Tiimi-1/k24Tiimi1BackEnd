

# Here is the most simple way of making the image of the app.
# run:
# ./gradlew build
# docker build -t  petshop .
# docker run -it petshop:0.0.1-SNAPSHOT
FROM openjdk:17
EXPOSE 8080
RUN mkdir /app
COPY build/libs/petshop-0.0.1-SNAPSHOT.jar /app/petshop.jar
ENTRYPOINT ["java","-jar","/app/petshop.jar"]


#Better way would be to make it 2-phased and build .jar file with gradle while building the image
#Something like:

# FROM gradle:8.7.0-jdk17-alpine AS build
# COPY --chown=gradle:gradle . /home/gradle/src
# WORKDIR /home/gradle/src
# RUN gradle build --no-daemon 

# FROM openjdk:17
# EXPOSE 8080
# RUN mkdir /app
# COPY --from=build /home/gradle/src/build/libs/petshop-0.0.1-SNAPSHOT.jar /app/petshop.jar
# ENTRYPOINT ["java","-jar","/app/petshop.jar"]
