FROM openjdk:11.0.6-jre-slim
ENV TZ="Asia/Kolkata"
ADD target/cms-gateway-service-0.0.1-SNAPSHOT.jar cms-gateway-service-0.0.1-SNAPSHOT.jar
EXPOSE 8010
CMD java -jar cms-gateway-service-0.0.1-SNAPSHOT.jar