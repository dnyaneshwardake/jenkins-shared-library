def call(){
  pipeline {
    agent any
    tools{
      "MAVEN_HOME"
    }
    stages {
        stage('Build') { 
            steps {
                sh 'mvn -B -DskipTests clean install' 
            }
        }
        
        stage('Deliver') {
            steps {
                sh 'echo Success!'
            }
        }
    }
}
}
