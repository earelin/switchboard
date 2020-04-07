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
  agent any

  tools {
    jdk 'openjdk-11'
  }

  options {
    ansiColor('xterm')
    disableConcurrentBuilds()
  }

  environment {
    BUILD_VERSION = "${env.TAG_NAME ? env.TAG_NAME : 'commit-' + env.GIT_COMMIT.substring(0, 7)}"
    GRADLE_OPTS = '-Dorg.gradle.daemon=false'
    MAIN_BRANCH = '1.x.x'
    PROJECT_NAME = 'switchboard'
  }

  stages {
    stage('Dependencies and cleanup') {
      steps {
        sh './gradlew clean'
      }
    }
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
              recordIssues aggregatingResults: true, sourceCodeEncoding: 'UTF-8',
                referenceJobName: 'Dashboard/switchboard/1.x.x', tools: [
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
              credentialsId: 'GITHUB_API_TOKEN',

              createCommentWithAllSingleFileComments: false,
              createSingleFileComments: true,
              commentOnlyChangedContent: true,
              keepOldComments: false,
              violationConfigs: [
                [parser: 'CHECKSTYLE', reporter: 'Checkstyle', pattern: '.*/build/reports/checkstyle/.*\\.xml\$'],
                [parser: 'CPD', reporter: 'CPD', pattern: '.*/build/reports/cpd/.*\\.xml\$'],
                [parser: 'FINDBUGS', reporter: 'Spotbugs', pattern: '.*/build/reports/spotbugs/.*\\.xml\$']
              ]
            ])
          }
        }

        stage('Vulnerabilities analysis') {
          when {
            branch "${env.MAIN_BRANCH}"
          }
          tools {
            nodejs 'NodeJS-12.16'
            snyk 'snyk-latest'
          }
          environment {
            SNYK_TOKEN = credentials('snyk-newsroom')
          }
          steps {
            sh 'snyk monitor --org=newsroom'
          }
        }
      }
    }

    stage('Build') {
      steps {
        sh './gradlew bootJar'
      }
    }

    stage('Functional testing') {
      when {
        anyOf {
          branch "${env.MAIN_BRANCH}"
          changeRequest()
        }
      }
      steps {
        sh '''
          ./gradlew docker
          docker-compose up -d
          timeout 300 bash -c 'while [[ "$(curl -s -o /dev/null -w "%{http_code}" http://localhost:9000/actuator/health)" != "200" ]]; do sleep 5; done'
          ./gradlew functionalTest
        '''
      }
      post {
        always {
          sh 'docker-compose down -v'
          cucumber fileIncludePattern: '**/*.json', jsonReportDirectory: 'test/server-functional/build/surefire-reports'
        }
      }
    }
  }
}
