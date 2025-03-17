# for dev
FROM openjdk:17-alpine
# for prod
# FROM openjdk:11.0.6-jre-slim 
ENV TZ="Asia/Kolkata"
ADD target/cms-auth-service-0.0.1-SNAPSHOT.jar cms-auth-service-0.0.1-SNAPSHOT.jar
EXPOSE 8011
CMD java -jar cms-auth-service-0.0.1-SNAPSHOT.jar --spring.cloud.kubernetes.secrets.enabled=true