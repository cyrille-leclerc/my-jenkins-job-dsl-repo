node('docker') {
  docker.image('cloudbees/java-build-tools').inside {
    git 'https://github.com/spring-projects/spring-petclinic.git'
    sh 'mvn clean package'
  }
}
