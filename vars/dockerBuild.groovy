def call(){
  pipeline{
    environment {
    registry = "awsadmindakets/dakets"
    registryCredential = 'dockerhub'
    }
    
    agent any
    
    stages {
    stage('Building image') {
      steps{
        script {
            docker.build registry + ":$BUILD_NUMBER"
          }
        }
      }
    }
  }
}
