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
    //added for task flow pro project error : FATAL ERROR: Reached heap limit Allocation failed - JavaScript heap out of memory
    sh 'export NODE_OPTIONS=--max_old_space_size=4096'
    sh 'ng build'
  }
}