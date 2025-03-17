#!/usr/bin/env groovy

def call() {
  container('git') {
    dir("gitops-data") {
      withCredentials([usernamePassword(credentialsId: 'COHERENT_GITOPS_DATA_TOKEN', passwordVariable: 'PASSWORD', usernameVariable: 'USERNAME')]) {
        try {
          sh "git config --global --add safe.directory '*'"
          
          // Show current deployment file before modification
          sh "cat $DEPLOYMENT_FILE_PATH"
          
          // Update image references
          sh "sed -i 's|$COHERENT_DOCKER_REGISTRY_URL/$PROJECT_NAME:[0-9]\\+|$IMAGE_NAME|g' $DEPLOYMENT_FILE_PATH"
          sh "sed -i 's|<IMAGENAME>:<TAG>|$IMAGE_NAME|g' $DEPLOYMENT_FILE_PATH"
          
          // Show modified deployment file after changes
          sh "cat $DEPLOYMENT_FILE_PATH"
          
          // Check Git status and proceed with commit if changes exist
          sh "git status"
          def changes = sh(script: "git status --porcelain", returnStdout: true).trim()
          
          if (changes) {
            // Proceed with commit and push only if changes are detected
            sh "git add ."
            sh "git config --global user.email 'infra@coherent.in'"
            sh "git config --global user.name 'Coherent Infra'"
            sh "git commit -m 'Updates from Jenkins agent'"
            
            // Configure remote and pull before pushing
            sh "git config --global pull.ff true"
            sh "git remote set-url origin https://${USERNAME}:${PASSWORD}@$GITOPS_DATA_URL_WITHOUT_HTTPS"
            sh "git pull origin master --rebase"
            sh "git push origin master"
          } else {
            echo "No changes detected. Skipping Git commit and push."
          }
        } catch (err) {
          error "Failed during publish deployment step: ${err.message}"
        }
      }
    }
  }
}
