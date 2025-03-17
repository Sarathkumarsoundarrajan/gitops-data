#!/usr/bin/env groovy

def call() {
  container('node') {
    sh "rm nginx.conf"
    sh "cp ${WORKSPACE}/${COMMON_PATH}/nginx-react-vite.conf ${WORKSPACE}/nginx.conf"
    sh "echo ${WORKSPACE}"
    sh 'cd ${WORKSPACE}'
    sh 'npm install --force'
    sh 'npm run build'
    sh 'ls dist'
  }
}