FROM openjdk:17-alpine
ENV TZ="Asia/Kolkata"
ADD target/raaga.auth.service-0.0.1-SNAPSHOT.jar raaga.auth.service-0.0.1-SNAPSHOT.jar
EXPOSE 8091
CMD java -jar raaga.auth.service-0.0.1-SNAPSHOT.jar