def call(){
  pipeline {
    agent any
    tools {
        maven "MAVEN_HOME"
    }
    stages { 
        stage('Build') { 
            steps {
                sh 'mvn -B -DskipTests clean install' 
            }
        }
        
        stage('Deliver') {
            steps {
                sh './jenkins/scripts/deliver.sh'
            }
        }
    }
}
}
