# for dev
FROM openjdk:17-alpine
# for prod
# FROM openjdk:11.0.6-jre-slim 
ENV TZ="Asia/Kolkata"
ADD target/cms-master-service-0.0.1-SNAPSHOT.jar cms-master-service-0.0.1-SNAPSHOT.jar
EXPOSE 8014
CMD java -jar cms-master-service-0.0.1-SNAPSHOT.jar