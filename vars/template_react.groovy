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
            command: ["cat"]
            tty: true
          - name: java-node
            image: timbru31/java-node
            command: ["cat"]
            tty: true
          - name: git
            image: bitnami/git:latest
            command: ["cat"]
            tty: true
          - name: kaniko
            image: gcr.io/kaniko-project/executor:v1.9.1-debug
            command: ["/busybox/cat"]
            tty: true
            volumeMounts:
            - mountPath: /kaniko/.docker
              name: kaniko-secret
            env:
            - name: DOCKER_CONFIG
              value: /kaniko/.docker
            - mountPath: /workspace
              name: workspace-volume
          volumes:
          - name: kaniko-secret
            secret:
              secretName: kaniko-secret
          - name: workspace-volume
            emptyDir: {}
      """
            }
        }

        environment {
            ENVIRONMENT = "${params.ENVIRONMENT ?: 'dev'}"
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
                    script {
                        echo "Cloning the repository..."
                        module_checkOutTheSourceCode()
                    }
                }
            }

            stage('Obtaining Config Files') {
                steps {
                    script {
                        echo "Obtaining config files..."
                        module_obtainingConfigFiles()
                    }
                }
            }

            stage('SonarQube Analysis') {
                when { expression { (ENABLE_SONAR ?: false) && util_isStringInList(ENVIRONMENT ?: "", SONAR_ENVIRONMENTS ?: []) } }
                steps {
                    script {
                        echo "Running SonarQube Analysis..."
                        module_js_sonarQubeAnalysis()
                    }
                }
            }

            stage('Quality Gate') {
                when { expression { (ENABLE_SONAR_QUALITY_GATE ?: false) && (ENABLE_SONAR ?: false) && util_isStringInList(ENVIRONMENT ?: "", SONAR_ENVIRONMENTS ?: []) } }
                steps {
                    script {
                        echo "Checking SonarQube Quality Gate..."
                        waitForQualityGate abortPipeline: true
                    }
                }
            }

            stage('Install and Build') {
                steps {
                    script {
                        echo "Installing dependencies and building React project..."
                        module_react_installAndBuild()
                    }
                }
            }

            stage('Build Container Image') {
                    steps {
                        container('kaniko') {
                            script {
                                echo "Building Docker Image with Kaniko..."
                                sh '''
                                    /kaniko/executor \
                                      --dockerfile=Dockerfile \
                                      --context=/workspace \
                                      --destination=docker.io/chd-portal:28 \
                                      --skip-tls-verify \
                                      --cache=true
                                '''
                            }
                        }
                    }
                }

            stage('Publish deployment') {
                steps {
                    script {
                        echo "Publishing deployment..."
                        module_publishDeployment()
                    }
                }
            }
        }
    }
}
