pipeline {
    agent any

    environment {
        APP_NAME     = 'tvpssms-sc'
        // ── Update DOCKER_HUB_USER to your Docker Hub username ──
        DOCKER_IMAGE = "erfansyabil/${APP_NAME}"
        DOCKER_TAG   = "${BUILD_NUMBER}"
        // ── Update to the actual Jira issue you want notified ──
        JIRA_ISSUE   = 'CS-2'
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Unit Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit testResults: '**/target/surefire-reports/*.xml',
                          allowEmptyResults: true
                }
            }
        }

        stage('Docker Build & Tag') {
            steps {
                sh """
                    docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} .
                    docker tag  ${DOCKER_IMAGE}:${DOCKER_TAG} ${DOCKER_IMAGE}:latest
                """
            }
        }

        stage('Docker Push') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'dockerhub-creds',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    sh """
                        echo "\$DOCKER_PASS" | docker login -u "\$DOCKER_USER" --password-stdin
                        docker push ${DOCKER_IMAGE}:${DOCKER_TAG}
                        docker push ${DOCKER_IMAGE}:latest
                    """
                }
            }
        }

        stage('Deploy') {
            steps {
                sh """
                    docker stop ${APP_NAME} || true
                    docker rm   ${APP_NAME} || true
                    docker run -d \\
                        --name ${APP_NAME} \\
                        -p 8085:8080 \\
                        -e SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3306/tvpssdb \\
                        -e SPRING_DATASOURCE_USERNAME=root \\
                        -e SPRING_DATASOURCE_PASSWORD=secret \\
                        ${DOCKER_IMAGE}:${DOCKER_TAG}
                    echo 'Waiting 20 s for app to start...'
                    sleep 20
                """
            }
        }

        stage('Load Test (JMeter)') {
            when {
                expression { fileExists('tests/load/tvpssms_load_test.jmx') }
            }
            steps {
                sh """
                    mkdir -p target/jmeter
                    jmeter -n \
                        -t tests/load/tvpssms_load_test.jmx \
                        -l target/jmeter/results.jtl
                """
            }
            post {
                always {
                    archiveArtifacts artifacts: 'target/jmeter/results.jtl',
                                     allowEmptyArchive: true
                }
            }
        }

        stage('Notify Jira') {
            steps {
                jiraComment issueKey: "${JIRA_ISSUE}",
                            body: "Build #${BUILD_NUMBER} PASSED — image pushed: ${DOCKER_IMAGE}:${DOCKER_TAG}"
            }
        }
    }

    post {
        failure {
            jiraComment issueKey: "${JIRA_ISSUE}",
                        body: "Build #${BUILD_NUMBER} FAILED — ${BUILD_URL}"
        }
    }
}
