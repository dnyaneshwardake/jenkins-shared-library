def call(){
  pipeline {
    environment {
      registry = "awsadmindakets/dakets"
      registryCredential = 'dockerhub'
      dockerImage=''
    }
    
    agent any
    
    tools {
      maven 'MAVEN_HOME' 
  }
    stages {
        stage('Maven-Build') { 
            steps {
                sh 'mvn -B -DskipTests clean install'
            }
        }
      stage('Docker-Build') { 
            steps{
              script {
                  dockerImage = docker.build registry + ":$BUILD_NUMBER"
                }
            }
        }
      stage('Docker-Push') { 
            steps{
              script {
                  docker.withRegistry( '', registryCredential ) {
                  dockerImage.push()
                }
            }
        }
        stag('Cleanup'){
          steps{
            sh 'docker rmi $registry:$BUILD_NUMBER"'
          }
        }
      
    }
  }
}
