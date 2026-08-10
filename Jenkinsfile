pipeline {
  agent any
  tools {
    jdk "Java 25"
    maven "Maven 3"
  }
  stages {
    stage("Build") {
      steps {
        withMaven {
          sh "./mvnw -DskipTests clean package"
        }
      }
    }
    stage("Test") {
      steps {
        sh "podman run -d -p 4444:4444 --name selenium imac:8082/docker-proxy/selenium/standalone-firefox:latest"
        withMaven {
          sh "./mvnw test"
        }
      }
      post {
        always {
          junit "target/surefire-reports/*.xml"
          sh "podman container stop selenium"
          sh "podman container ls --all --filter name=selenium --quiet | xargs podman container rm"
        }
      }
    }
  }
  post {
    always {
      emailext  recipientProviders: [developers(), requestor()],
                subject: "Jenkins Build Notification: ${currentBuild.fullDisplayName}",
                body: """\
                  Build Status: ${currentBuild.currentResult}
                  Project: ${env.JOB_NAME}
                  Build Number: ${env.BUILD_NUMBER}
                  Build URL: ${env.BUILD_URL}
                  """
    }
  }
}
