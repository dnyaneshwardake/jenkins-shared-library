def call(){
  pipeline {
    environment {
      registry = "awsadmindakets/dakets"
      registryCredential = 'dockerhub'
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
                  sh 'docker.build registry + ":$BUILD_NUMBER"'
                }
            }
        }
      
    }
  }
}
