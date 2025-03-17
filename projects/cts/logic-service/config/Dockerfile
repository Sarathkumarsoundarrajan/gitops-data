FROM openjdk:17-alpine
ENV TZ="Asia/Kolkata"
ADD target/coherent-tax-system-0.0.1-SNAPSHOT.jar coherent-tax-system-0.0.1-SNAPSHOT.jar
EXPOSE 8011
ENTRYPOINT ["java", "-jar", "coherent-tax-system-0.0.1-SNAPSHOT.jar"]
