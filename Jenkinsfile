pipeline {
    agent any

    environment {
        TOMCAT_WEBAPPS = 'C:/Program Files/Apache Software Foundation/Tomcat 10.1/webapps'
        // A temporary place to put the file so Tomcat doesn't see it early
        TEMP_STAGING = 'C:/temp'
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

                    // Create temp directory if it doesn't exist
                    bat "if not exist \"${TEMP_STAGING}\" mkdir \"${TEMP_STAGING}\""

                    // 1. Stop Tomcat (Force success even if already stopped)
                    echo "Stopping Tomcat..."
                    bat "net stop \"${SERVICE_NAME}\" /y || ver > nul"

                    // 2. Clean up old files
                    echo "Cleaning webapps folder..."
                    bat "if exist \"${TOMCAT_WEBAPPS}\\${WAR_NAME}\" del /f /q \"${TOMCAT_WEBAPPS}\\${WAR_NAME}\""
                    bat "if exist \"${TOMCAT_WEBAPPS}\\${folderName}\" rd /s /q \"${TOMCAT_WEBAPPS}\\${folderName}\""

                    // 3. The "Two-Step" Copy (The Secret Fix)
                    echo "Deploying WAR..."
                    // First, copy to temp (Tomcat can't see this)
                    bat "copy /y target\\*.war \"${TEMP_STAGING}\\${WAR_NAME}\""
                    // Second, move to webapps (This is instant/atomic)
                    bat "move /y \"${TEMP_STAGING}\\${WAR_NAME}\" \"${TOMCAT_WEBAPPS}\\${WAR_NAME}\""

                    // 4. Start Tomcat
                    echo "Starting Tomcat..."
                    bat "net start \"${SERVICE_NAME}\""
                }
            }
        }
    }
}