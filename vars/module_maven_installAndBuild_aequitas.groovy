#!/usr/bin/env groovy

def call() {

  container('maven') {
    sh 'mvn -Dmaven.test.failure.ignore=true clean package'
  }

}
