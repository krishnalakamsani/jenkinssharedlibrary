def call (String gitBranch = 'main', String gitUrl = 'https://github.com/krishnalakamsani/fleetapp.git', String imageName = 'krishnalakamsani/fleetappapi', String path = 'sourcecode/k8s-fleetman-api-gateway/src') {
pipeline {
  agent {
      label 'docker'
    }
     stages {
    stage('Checkout') {
      steps {
        git branch: '${gitBranch}', url: '${gitUrl}'
        }
    }
    stage('Build') {
      steps {
        sh 'cd "$path"'
        sh 'mvn clean package'
        }
    }
    stage('Build and Push Docker Image') {
      steps {
        sh 'docker build -t $imageName:$BUILD_NUMBER .'
        sh 'docker push $imageName:$BUILD_NUMBER'
        }
    }
  }
}
}
