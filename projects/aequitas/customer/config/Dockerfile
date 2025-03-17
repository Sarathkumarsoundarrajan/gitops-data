FROM amazoncorretto:11-alpine-jdk
ENV TZ="Asia/Kolkata"
ADD target/acs-customer-service-0.0.1-SNAPSHOT.jar acs-customer-service-0.0.1-SNAPSHOT.jar
EXPOSE 7010
CMD java -jar acs-customer-service-0.0.1-SNAPSHOT.jar