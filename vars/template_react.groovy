#!/usr/bin/env groovy

def call(
    String PROJECT_SCM_URL,
    String PROJECT_NAME,
    String GITOPS_DATA_PROJECT_PATH,
    String NODE_IMAGE,
    Boolean ENABLE_SONAR,
    Boolean ENABLE_SONAR_QUALITY_GATE,
    List<String> SONAR_ENVIRONMENTS
) {
    pipeline {
        agent {
            kubernetes {
                yaml """
        apiVersion: v1
        kind: Pod
        metadata:
          labels:
            app: jenkins-agent
        spec:
          containers:
          - name: node
            image: "${NODE_IMAGE ?: 'node:18-alpine'}"
            command:
            - cat
            tty: true
          - name: java-node
            image: timbru31/java-node
            command:
            - cat
            tty: true
          - name: git
            image: bitnami/git:latest
            command:
            - cat
            tty: true
      """
            }
        }
        environment {
            IMAGE_NAME = "${COHERENT_DOCKER_REGISTRY_URL}/${PROJECT_NAME}:${BUILD_NUMBER}"
            PREVIOUS_BUILD_NUMBER = util_subtractAndConvert("${BUILD_NUMBER}")
            DEPLOYMENT_FILE_PATH = "projects/${GITOPS_DATA_PROJECT_PATH}/k8s-config/${ENVIRONMENT}/deployment.yaml"
            PROJECT_CONFIG_PATH = "gitops-data/projects/${GITOPS_DATA_PROJECT_PATH}/config"
            COMMON_PATH = "gitops-data/common"
            COHERENT_GITOPS_DATA_TOKEN = credentials('COHERENT_GITOPS_DATA_TOKEN')
            PROJECT_NAME = "${PROJECT_NAME}"
            PROJECT_SCM_URL = "${PROJECT_SCM_URL}"
        }

        stages {
            stage('Check out the Source Code') {
                steps {
                    module_checkOutTheSourceCode()
                }
            }

            stage('Obtaining Config Files') {
                steps {
                    module_obtainingConfigFiles()
                }
            }

            stage('SonarQube Analysis') {
                when { expression { ENABLE_SONAR && util_isStringInList(ENVIRONMENT, SONAR_ENVIRONMENTS) } }
                steps {
                    module_js_sonarQubeAnalysis()
                }
            }

            stage('Quality Gate') {
                when { expression { ENABLE_SONAR_QUALITY_GATE && ENABLE_SONAR && util_isStringInList(ENVIRONMENT, SONAR_ENVIRONMENTS) } }
                steps {
                    waitForQualityGate abortPipeline: true
                }
            }

            stage('Install and Build') {
                steps {
                    module_react_installAndBuild()
                }
            }

            stage('Build Container Image') {
                steps {
                    module_BuildContainerImage()
                }
            }

            stage('Publish deployment') {
                steps {                    
                    module_publishDeployment()
                }
            }
        }
    }
}
