def call (String gitBranch = 'main', String gitUrl = 'https://github.com/krishnalakamsani/fleetapp.git', String imageName = 'krishnalakamsani/fleetappapi', String path = '/home/ubuntu/jenkins/workspace/fleetapp-k8s-fleetman-api-gateway/sourcecode/k8s-fleetman-api-gateway') {
pipeline {
  agent {
      label 'docker'
    }
     stages {
    stage('Checkout') {
      steps {
        git branch: 'main', url: 'https://github.com/krishnalakamsani/fleetapp.git'
        }
    }
    stage('Build') {
      steps {
        sh 'cd sourcecode/k8s-fleetman-api-gateway'
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
