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
                powershell './mvnw clean package -DskipTests'
            }
        }

    stage('Deploy Locally') {
    steps {
        script {
            // 1. Ensure the build actually finished and the file is stable
            sleep 3 
            
            // 2. Stop Tomcat and WAIT for it to fully release files
            echo "Stopping Tomcat..."
            bat "net stop \"${SERVICE_NAME}\" /y || ver > nul"
            sleep 5

            // 3. Delete old app folder (IMPORTANT: sometimes Windows holds the folder)
            def folderPath = "${TOMCAT_WEBAPPS}\\myapp"
            bat "if exist \"${folderPath}\" rd /s /q \"${folderPath}\""
            bat "if exist \"${TOMCAT_WEBAPPS}\\myapp.war\" del /f /q \"${TOMCAT_WEBAPPS}\\myapp.war\""

            // 4. Copy to Temp first, then move
            echo "Moving WAR to Tomcat..."
            bat "copy /y target\\*.war \"C:\\temp\\myapp.war\""
            
            // This 'move' is the only thing Tomcat will ever see
            bat "move /y \"C:\\temp\\myapp.war\" \"${TOMCAT_WEBAPPS}\\myapp.war\""

            // 5. Start Tomcat
            bat "net start \"${SERVICE_NAME}\""
        }
    }
    }
    }
}