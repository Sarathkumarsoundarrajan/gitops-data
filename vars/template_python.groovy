#!/usr/bin/env groovy

def call(
String PROJECT_SCM_URL,
String PROJECT_NAME,
String GITOPS_DATA_PROJECT_PATH
) {
    pipeline {
        agent {
            kubernetes {
                yaml '''
        apiVersion: v1
        kind: Pod
        metadata:
          labels:
            app: dms-ml
        spec:
          containers:
          - name: python
            image: python:3.9
            command:
            - cat
            tty: true
          - name: git
            image: bitnami/git:latest
            command:
            - cat
            tty: true
          - name: kaniko
            image: gcr.io/kaniko-project/executor:debug
            command:
            - cat
            tty: true
            volumeMounts:
            - mountPath: "/workspace"
              name: "workspace-volume"
              readOnly: false
            - name: kaniko-secret
              mountPath: /kaniko/.docker             
          volumes:
          - name: kaniko-secret
            secret:
              secretName: registry-regcred
              items:
                - key: .dockerconfigjson
                  path: config.json
      '''
            }
        }
        environment {
            IMAGE_NAME = "${COHERENT_DOCKER_REGISTRY_URL}/${PROJECT_NAME.toLowerCase()}:${BUILD_NUMBER}"
            DEPLOYMENT_FILE_PATH = "projects/${GITOPS_DATA_PROJECT_PATH}/k8s-config/dev/deployment.yaml"
            PROJECT_CONFIG_PATH = "gitops-data/projects/${GITOPS_DATA_PROJECT_PATH}/config"
            COMMON_PATH = "gitops-data/common"
            COHERENT_GITOPS_DATA_TOKEN = credentials('COHERENT_GITOPS_DATA_TOKEN')
            PROJECT_NAME = "${PROJECT_NAME}"
            PROJECT_SCM_URL = "${PROJECT_SCM_URL}"
        }

        stages {

            stage('Check out Source Code') {
                steps {
                    module_checkOutTheSourceCode()
                }
            }

            stage('Obtaining Config Files') {
                steps {
                    module_obtainingConfigFiles()
                }
            }

            stage('Install Dependencies') {
                steps {
                    container('python') {
                        sh 'pip install -r requirements.txt'
                    }
                }
            }

            stage('Build Container Image') {
                steps {
                     module_BuildContainerImage()
                }
            }

            stage('Publish Deployment') {
                steps {
                    module_publishDeployment()
                }
            }

        }
    }
}
