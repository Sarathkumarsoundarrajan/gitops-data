#!/usr/bin/env groovy

def call() {

  container('java-node') {
    script {
      def scannerHome = tool 'SonarScanner'
      withSonarQubeEnv {
        sh "${scannerHome}/bin/sonar-scanner \
          -Dsonar.projectKey=$PROJECT_NAME-hTDqPuJSI2rlRIrk \
          -Dsonar.sources=. \
          -Dsonar.host.url=$SONAR_HOST_URL " +
          '-Dsonar.token=$SONAR_TOKEN'
      }
    }
  }

}