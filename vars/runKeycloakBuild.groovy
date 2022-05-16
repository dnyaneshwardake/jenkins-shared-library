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
                //sh 'mvn -B -DskipTests clean install'
                //sh 'mvn clean install -DskipTests'
                sh 'mvn -Pdistribution -pl distribution/server-dist -am -Dmaven.test.skip clean install'
            }
        }
      stage('Build-Quarkus-Dist') { 
            steps {
                //sh 'mvn -B -DskipTests clean install'
                //sh 'mvn clean install -DskipTests'
                sh 'cd quarkus'
                sh 'mvn -f dist/pom.xml clean install'
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
