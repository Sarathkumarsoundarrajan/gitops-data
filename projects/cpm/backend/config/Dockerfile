# for dev
FROM openjdk:17-alpine
# for prod
# FROM openjdk:11.0.6-jre-slim 
ENV TZ="Asia/Kolkata"
ADD target/cpm_service_springboot-0.0.1-SNAPSHOT.jar cpm_service_springboot-0.0.1-SNAPSHOT.jar
EXPOSE 8080
CMD java -jar cpm_service_springboot-0.0.1-SNAPSHOT.jar --spring.cloud.kubernetes.secrets.enabled=true
