FROM openjdk:17-alpine
ENV TZ="Asia/Kolkata"
ADD target/weather-dashboard-0.0.1-SNAPSHOT.jar weather-dashboard-0.0.1-SNAPSHOT.jar
EXPOSE 8095
CMD java -jar weather-dashboard-0.0.1-SNAPSHOT.jar