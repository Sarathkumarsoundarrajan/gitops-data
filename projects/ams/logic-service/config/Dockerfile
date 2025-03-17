FROM openjdk:11.0.6-jre-slim
ENV TZ="Asia/Kolkata"
ADD target/inventory-logic-service-0.0.1-SNAPSHOT.jar inventory-logic-service-0.0.1-SNAPSHOT.jar
EXPOSE 8012
CMD java -jar inventory-logic-service-0.0.1-SNAPSHOT.jar