FROM openjdk:17-jdk-slim

ENV TZ="Asia/Kolkata"

ADD target/hcp-management-service-0.0.1-SNAPSHOT.jar hcp-management-service-0.0.1-SNAPSHOT.jar

RUN addgroup --system javagroup && adduser --system --ingroup javagroup javauser

USER javauser

EXPOSE 8080

CMD java -jar hcp-management-service-0.0.1-SNAPSHOT.jar
