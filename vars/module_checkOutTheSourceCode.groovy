#!/usr/bin/env groovy

def call() {

  git branch: "${ENVIRONMENT}",
    credentialsId: 'GitLab-Access-token',
    url: "$PROJECT_SCM_URL"
  dir("gitops-data") {
    git branch: 'master',
      credentialsId: 'COHERENT_GITOPS_DATA_TOKEN',
      url: "https://$GITOPS_DATA_URL_WITHOUT_HTTPS"
  }
  sh "ls ${WORKSPACE}"

}
