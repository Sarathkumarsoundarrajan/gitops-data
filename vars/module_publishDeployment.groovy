#!/usr/bin/env groovy

def call() {
  container('git') {
    dir("gitops-data") {
      withCredentials([usernamePassword(credentialsId: 'COHERENT_GITOPS_DATA_TOKEN', passwordVariable: 'PASSWORD', usernameVariable: 'USERNAME')]) {
        sh "git config --global --add safe.directory '*'"
        sh "cat $DEPLOYMENT_FILE_PATH"
        sh "sed -i 's|$COHERENT_DOCKER_REGISTRY_URL/$PROJECT_NAME:[0-9]\\+|$IMAGE_NAME|g' $DEPLOYMENT_FILE_PATH"
        sh "sed -i 's|<IMAGENAME>:<TAG>|$IMAGE_NAME|g' $DEPLOYMENT_FILE_PATH"
        sh "cat $DEPLOYMENT_FILE_PATH"
        sh "git status"
        sh "git add ."
        sh "git config --global user.email 'infra@coherent.in'"
        sh "git config --global user.name 'Coherent Infra'"
        sh "git commit -m 'Updates from jenkiins agent'"
        sh "git config --global pull.ff true"
        sh 'git remote set-url origin https://${USERNAME}:${PASSWORD}@$GITOPS_DATA_URL_WITHOUT_HTTPS'
        sh "git pull origin master"
        sh "git push origin master"
      }
    }

  }
}