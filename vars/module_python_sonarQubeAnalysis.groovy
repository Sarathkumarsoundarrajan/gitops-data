def call() {
  container('python') {
    script {
      withSonarQubeEnv {
        sh """
          # Set up the environment
          python -m venv venv
          . venv/bin/activate
          
          # Install project dependencies
          pip install -r requirements.txt
          
          # Run SonarQube analysis
          sonar-scanner \
          -Dsonar.projectKey=$PROJECT_NAME \
          -Dsonar.projectName=$PROJECT_NAME \
          -Dsonar.host.url=$SONAR_HOST_URL \
          -Dsonar.token=$SONAR_TOKEN \
          -Dsonar.sources=. \
          -Dsonar.python.version=3.x
        """
      }
    }
  }
}
