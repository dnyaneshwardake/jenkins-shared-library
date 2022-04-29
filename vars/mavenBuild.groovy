def call(){
  pipeline {
    agent any
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
