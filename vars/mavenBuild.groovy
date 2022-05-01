def call(){
  pipeline {
    environment {
      registry = "https://index.docker.io/v2/"
      registryCredential = 'dockerhub'
      dockerImage="code-with-quarkus"
    }
    
    agent any
    
    tools {
      maven 'MAVEN_HOME'
      dockerTool 'DOCKER_ROOT'
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
                  docker.withRegistry( registry , registryCredential ) {
                  dockerImage.push()
                }
            }
        }
      }
      
    }
  }
}
