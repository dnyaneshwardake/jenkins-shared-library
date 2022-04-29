def call(){
  pipeline {
    any
    tools {
        maven "MAVEN_HOME"
    }
    stages { 
        stage('Build') { 
            steps {
                sh 'mvn -B -DskipTests clean install' 
            }
        }
    }
    
    
  }
}
