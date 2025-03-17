#!/usr/bin/env groovy

def call() {

  sh "cp ${WORKSPACE}/${COMMON_PATH}/nginx.conf ${WORKSPACE}/"
  sh "cp ${WORKSPACE}/${PROJECT_CONFIG_PATH}/Dockerfile ${WORKSPACE}/"
  sh "ls ${WORKSPACE}"
  // sh "cat ${WORKSPACE}/src/main/resources/bootstrap.yml"

}