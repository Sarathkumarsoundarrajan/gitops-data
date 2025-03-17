# for dev
FROM openjdk:17-alpine
# for prod
# FROM openjdk:11.0.6-jre-slim 
ENV TZ="Asia/Kolkata"
ADD target/cms-bug-tracker-service-0.0.1-SNAPSHOT.jar cms-bug-tracker-service-0.0.1-SNAPSHOT.jar
ADD src/main/resources/Coherent.png Coherent.png
EXPOSE 8013
CMD java -jar cms-bug-tracker-service-0.0.1-SNAPSHOT.jar