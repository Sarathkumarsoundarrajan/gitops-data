#!/usr/bin/env groovy

def call() {

  container('kaniko') {
    sh "/kaniko/executor --context ${WORKSPACE} --destination=gcr.io/kaniko-project/executor:latest"

  }

}
