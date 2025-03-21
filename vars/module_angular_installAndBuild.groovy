def call() {
  container('node') {
    sh "echo ${WORKSPACE}"
    sh 'cd ${WORKSPACE}'
    sh 'npm install --force'
    sh 'npm install -g @angular/cli'
    sh 'ng build'
  }
}
