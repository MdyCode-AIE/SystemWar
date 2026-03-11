pipeline {
    agent any
 
    tools {
        maven 'Maven_3.9.13' // Must match the name in Jenkins Global Tool Configuration
        jdk 'JDK 17'     // Must match the name in Jenkins Global Tool Configuration
    }
 
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
 
        stage('Build') {
            steps {
                // Using -DskipTests to avoid the MySQL connection error
                bat './mvnw clean package -DskipTests'
            }
        }
 
        stage('Deploy') {
            steps {
                echo 'Deploying to Tomcat on port 8086...'
                bat '''
                set TOMCAT_WEBAPPS=C:\\Program Files\\Apache Software Foundation\\Tomcat 10.1\\webapps
                if exist "%TOMCAT_WEBAPPS%\\systemwar-0.0.1-SNAPSHOT.war" del /f /q "%TOMCAT_WEBAPPS%\\systemwar-0.0.1-SNAPSHOT.war"
                if exist "%TOMCAT_WEBAPPS%\\systemwar-0.0.1-SNAPSHOT" rd /s /q "%TOMCAT_WEBAPPS%\\systemwar-0.0.1-SNAPSHOT"
                copy "target\\systemwar-0.0.1-SNAPSHOT.war" "%TOMCAT_WEBAPPS%\\"
                '''
            }
        }
    }
}