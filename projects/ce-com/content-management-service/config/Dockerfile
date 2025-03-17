FROM openjdk:17-alpine

ENV TZ="Asia/Kolkata"

ADD target/ecs-0.0.1-SNAPSHOT.jar ecs-0.0.1-SNAPSHOT.jar

EXPOSE 8012

CMD java -jar ecs-0.0.1-SNAPSHOT.jar
