def call(){
  pipeline {
    environment {
      registry = "awsadmindakets/keycloak"
      registryCredential = 'dockerhub'
      dockerImage="keycloak"
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
                  docker.withRegistry( '' , registryCredential ) {
                  dockerImage.push()
                }
            }
        }
      }
      stage('Cleaning up') {
        steps{
          sh "docker rmi $registry:$BUILD_NUMBER"
        }
      }
    }
  }
}
