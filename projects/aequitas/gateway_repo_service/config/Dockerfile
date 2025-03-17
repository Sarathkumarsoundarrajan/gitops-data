FROM openjdk:17-alpine

ENV TZ="Asia/Kolkata"

ADD target/acs-gateway-service-0.0.1-SNAPSHOT.jar acs-gateway-service-0.0.1-SNAPSHOT.jar

EXPOSE 8090

CMD java -jar acs-gateway-service-0.0.1-SNAPSHOT.jar
