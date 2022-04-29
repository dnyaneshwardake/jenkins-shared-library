def call(){
  pipeline {
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
            steps {
                dockerBuild()
            }
        }
      
    }
  }
}
