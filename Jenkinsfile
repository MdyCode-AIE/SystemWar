pipeline {
    agent any

    environment {
        // 1. Use double backslashes (\\) or forward slashes (/) for Windows paths
        // 2. Put the path in single quotes to avoid interpolation issues
        TOMCAT_WEBAPPS = 'C:/Program Files/Apache Software Foundation/Tomcat 10.1/webapps'
        WAR_NAME = "myapp.war" 
    }

    stages {
        stage('Build') {
            steps {
                // Use 'bat' instead of 'sh' for Windows
                bat 'mvnw.cmd clean package -DskipTests'
            }
        }

        stage('Deploy Locally') {
            steps {
                script {
                    // Use Windows 'del' and 'rd' commands via 'bat'
                    // We wrap the path in double quotes in case there are spaces in the folder name
                    
                    // Remove existing WAR
                    bat "if exist \"${TOMCAT_WEBAPPS}\\${WAR_NAME}\" del /f /q \"${TOMCAT_WEBAPPS}\\${WAR_NAME}\""
                    
                    // Remove exploded folder (the name of the WAR without .war)
                    def folderName = WAR_NAME.replace('.war', '')
                    bat "if exist \"${TOMCAT_WEBAPPS}\\${folderName}\" rd /s /q \"${TOMCAT_WEBAPPS}\\${folderName}\""
                    
                    // Copy new WAR using 'copy' command
                    bat "copy /y target\\*.war \"${TOMCAT_WEBAPPS}\\${WAR_NAME}\""
                }
            }
        }
    }
}