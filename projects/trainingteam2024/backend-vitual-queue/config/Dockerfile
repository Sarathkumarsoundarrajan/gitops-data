FROM openjdk:17-alpine
ENV TZ="Asia/Kolkata"
ADD target/virtual-queue-0.0.1-SNAPSHOT.jar virtual-queue-0.0.1-SNAPSHOT.jar
EXPOSE 8094
CMD java -jar virtual-queue-0.0.1-SNAPSHOT.jar