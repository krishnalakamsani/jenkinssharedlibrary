def call (String gitBranch = 'a', String gitUrl = 'a', String imageName = 'a', String path = 'a') {
pipeline {
  agent {
      label 'docker'
    }
     stages {
    stage('Checkout') {
      steps {
        git branch: '$gitBranch', url: '$gitUrl'
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
