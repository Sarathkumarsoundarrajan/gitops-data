#!/usr/bin/env groovy

def call() {
  container('node') {
    sh "echo ${WORKSPACE}"
    sh 'cd ${WORKSPACE}'
    sh 'npm install --force'
    def angularCliInstallCommand = "npm install -g "
    if ("$ANGULAR_VERSION" && "$ANGULAR_VERSION" != "") {
      angularCliInstallCommand += "$ANGULAR_VERSION"
    } else {
      angularCliInstallCommand += "@angular/cli"
    }
    sh angularCliInstallCommand
    sh 'ng build --configuration=production'
  }
}