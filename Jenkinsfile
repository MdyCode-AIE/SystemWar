pipeline {
    agent any

    environment {
        TOMCAT_WEBAPPS = 'C:/Program Files/Apache Software Foundation/Tomcat 10.1/webapps'
        WAR_NAME = "myapp.war" 
        SERVICE_NAME = "Tomcat10"
    }

    stages {
        stage('Build') {
            steps {
                bat 'mvnw.cmd clean package -DskipTests'
            }
        }

        stage('Deploy Locally') {
            steps {
                script {
                    def folderName = WAR_NAME.replace('.war', '')

                    // 1. Stop Tomcat. The "|| ver > nul" trick tells Jenkins: 
                    // "If stopping fails (because it's already stopped), just keep going."
                    bat "net stop \"${SERVICE_NAME}\" || ver > nul"

                    // 2. Clean old files from webapps
                    bat "if exist \"${TOMCAT_WEBAPPS}\\${WAR_NAME}\" del /f /q \"${TOMCAT_WEBAPPS}\\${WAR_NAME}\""
                    bat "if exist \"${TOMCAT_WEBAPPS}\\${folderName}\" rd /s /q \"${TOMCAT_WEBAPPS}\\${folderName}\""

                    // 3. Copy the WAR. 
                    // Using target\\*.war ensures it finds your file regardless of the version name.
                    bat "copy /y target\\*.war \"${TOMCAT_WEBAPPS}\\${WAR_NAME}\""

                    // 4. Start Tomcat
                    bat "net start \"${SERVICE_NAME}\""
                }
            }
        }
    }
}