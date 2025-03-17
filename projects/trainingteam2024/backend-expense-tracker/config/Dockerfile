FROM openjdk:17-alpine
ENV TZ="Asia/Kolkata"
ADD target/expense-tracker-0.0.1-SNAPSHOT.jar expense-tracker-0.0.1-SNAPSHOT.jar
EXPOSE 8092
CMD java -jar expense-tracker-0.0.1-SNAPSHOT.jar