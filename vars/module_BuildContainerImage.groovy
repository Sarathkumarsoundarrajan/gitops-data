#!/usr/bin/env groovy

def call() {

  container('kaniko') {
   sh " /kaniko/executor \
                      --dockerfile=Dockerfile \
                      --context=/workspace \
                      --destination=docker.io/$IMAGE_NAME \
                      --skip-tls-verify \
                      --cache=true"
  }

}
