#!/usr/bin/env groovy

def call() {

  container('kaniko') {
    sh "/kaniko/executor --context ${WORKSPACE} --destination $IMAGE_NAME"
  }

}
