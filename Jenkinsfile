pipeline {
    agent any
    tools {
        maven "MAVEN3"
        jdk "JAVA17"
    }
    stages {
        stage('Test') {
            steps {
                sh 'mvn spotless:check test'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn install'
            }
        }

        stage('Checkstyle analysis') {
            steps {
                sh 'mvn checkstyle:checkstyle'
             }
        }

        stage('Sonar Analysis') {
            environment {
                scannerHome = tool 'sonar5.0'
            }
            steps {
                withSonarQubeEnv('sonar') {
                    sh '''${scannerHome}/bin/sonar-scanner \
                    -Dsonar.projectName=suggestions-api \
                    -Dsonar.projectKey=suggestions-api \
                    -Dsonar.projectVersion=1.0 \
                    -Dsonar.sources=src/ \
                    -Dsonar.java.binaries=target/test-classes/com/lamt/suggestionsapi \
                    -Dsonar.junit.reportPaths=target/surefire-reports/*.xml \
                    -Dsonar.java.checkstyle.reportPaths=target/checkstyle-result.xml \
                    '''
                }
            }
        }

        // stage('Quality Gate') {
        //     steps {
        //         timeout(time: 1, unit: 'HOURS') {
        //             waitForQualityGate abortPipeline: true
        //         }
        //     }
        // }

        stage('Upload to Nexus') {
            environment {
                NEXUS_URL = '172.31.25.130:8081'
                NEXUS_REPO = 'suggestions-api-repo'
                NEXUS_CREDENTIALS_ID = 'nexuslogin'
            }
            steps {
                nexusArtifactUploader(
                nexusVersion: 'nexus3',
                protocol: 'http',
                nexusUrl: NEXUS_URL,
                groupId: 'com.lamt',
                version: "${env.BUILD_ID}-${env.BUILD_TIMESTAMP}",
                repository: NEXUS_REPO,
                credentialsId: NEXUS_CREDENTIALS_ID,
                artifacts: [
                    [artifactId: 'suggestions-api',
                    file: 'target/suggestions-api-0.0.1-SNAPSHOT.jar',
                    type: 'jar']
                ]
            )
            }
        }
    }
}