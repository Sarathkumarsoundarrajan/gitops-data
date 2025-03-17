#!/usr/bin/env groovy

def call() {

  container('java-node') {
    script {
      withSonarQubeEnv {
        container('maven') {
          sh 'mvn clean verify sonar:sonar \
          -Dsonar.projectKey=$PROJECT_NAME-u2xM61MyFBqIqMmc \
          -Dsonar.projectName=$PROJECT_NAME \
          -Dsonar.host.url=$SONAR_HOST_URL \
          -Dsonar.token=$SONAR_TOKEN'
        }
      }
    }
  }

}
