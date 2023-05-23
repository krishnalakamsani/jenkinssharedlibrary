def call (String label = 'a', String gitBranch = 'a', String gitUrl = 'a', String imageName = 'a', String dockerhubCred = 'a', String path = 'a')
pipeline {
  agent {
      label '$label'
    }
    environment {
		LOGIN_CREDENTIALS=credentials('$dockerhubCred')
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
        sh 'echo $LOGIN_CREDENTIALS_PSW | docker login -u $LOGIN_CREDENTIALS_USR --password-stdin'
        sh 'docker push $imageName:$BUILD_NUMBER'
        }
    }
  }
}
