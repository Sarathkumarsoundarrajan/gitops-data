FROM openjdk:17-alpine

ENV TZ="Asia/Kolkata"

ADD target/acs-authorization-0.0.1-SNAPSHOT.jar acs-authorization-0.0.1-SNAPSHOT.jar

EXPOSE 7010

CMD java -jar acs-authorization-0.0.1-SNAPSHOT.jar  --spring.cloud.kubernetes.secrets.enabled=true
