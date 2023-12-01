# inherit from image
FROM openjdk:17

# copy the spring boot application jar from target folder and rename to app.jar
COPY target/*.jar app.jar

# when container starts up, run the 'java -jar /app.jar' command to start the spring boot application
ENTRYPOINT ["java","-jar","/app.jar"]