/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

pipeline {
  agent {
    docker {
      image 'openjdk:11.0-jdk'
    }
  }

  options {
    ansiColor('xterm')
    disableConcurrentBuilds()
  }

  environment {
    PROJECT_NAME = 'switchboard'
    MAIN_BRANCH = '1.x.x'
    BUILD_VERSION = "${env.TAG_NAME ? env.TAG_NAME : 'commit-' + env.GIT_COMMIT.substring(0, 7)}"
  }

  stages {
    stage('Server') {
      when {
        not { buildingTag() }
      }
      stages {
        stage('Code analisys and testing') {
          steps {
            sh './gradlew :server:check'
          }
          post {
            always {
              junit 'server/build/test-results/**/*.xml'
              recordIssues aggregatingResults: true, sourceCodeEncoding: 'UTF-8', tools: [
                checkStyle(pattern: 'server/build/reports/checkstyle/*.xml'),
                cpd(pattern: 'server/build/reports/cpd/*.xml'),
                spotBugs(pattern: 'server/build/reports/spotbugs/*.xml', useRankAsPriority: true)
              ]
            }
            success {
              jacoco classPattern: 'server/build/classes', execPattern: 'server/build/jacoco/*.exec', sourcePattern: 'server/src/main/java'
            }
          }
        }

        stage('Comment pull request') {
          when { changeRequest() }
          environment {
            REPOSITORY_NAME = "${env.GIT_URL.tokenize('/')[3].split('\\.')[0]}"
            REPOSITORY_OWNER = "${env.GIT_URL.tokenize('/')[2]}"
          }
          steps {
            ViolationsToGitHub([
              gitHubUrl: env.GIT_URL,
              repositoryName: env.REPOSITORY_NAME,
              pullRequestId: env.CHANGE_ID,
              repositoryOwner: env.REPOSITORY_OWNER,

              createCommentWithAllSingleFileComments: false,
              createSingleFileComments: true,
              commentOnlyChangedContent: true,
              keepOldComments: false,
              violationConfigs: [
                [parser: 'CHECKSTYLE', reporter: 'Checkstyle', pattern: 'server/build/reports/checkstyle/.*\\.xml'],
                [parser: 'CPD', reporter: 'CPD', pattern: 'server/build/reports/cpd/.*\\.xml'],
                [parser: 'FINDBUGS', reporter: 'Spotbugs', pattern: 'server/build/reports/spotbugs/.*\\.xml']
            ]])
          }
        }
      }    
    }
    stage('Build') {
      steps {
        sh './gradlew bootJar'
      }
    }
    stage('Clean up') {
      steps {
        script {
          deleteDir()
        }
      }
    }
  }
}
