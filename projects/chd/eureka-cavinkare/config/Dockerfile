FROM openjdk:17-alpine
ENV TZ="Asia/Kolkata"
ADD target/chd-eureka-0.0.1-SNAPSHOT.jar chd-eureka-0.0.1-SNAPSHOT.jar
EXPOSE 8761
CMD java -jar chd-eureka-0.0.1-SNAPSHOT.jar