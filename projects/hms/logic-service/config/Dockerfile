FROM gcr.io/distroless/java11-debian11:latest
ENV TZ="Asia/Kolkata"
ADD target/hms-master-service-0.0.1-SNAPSHOT.jar hms-master-service-0.0.1-SNAPSHOT.jar
EXPOSE 8010
ENTRYPOINT ["java", "-jar", "hms-master-service-0.0.1-SNAPSHOT.jar"]
