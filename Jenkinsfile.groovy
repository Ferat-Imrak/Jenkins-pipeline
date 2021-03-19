properties([
    buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '10', numToKeepStr: '10')), 
    disableConcurrentBuilds(), parameters([booleanParam(defaultValue: true, description: 'apply', name: 'ACTION'), 
    choice(choices: ['dev', 'qa', 'stage', 'prod'], description: 'Which Environment Build?', name: 'ENVIRONMENT')]), 
    pipelineTriggers([cron('H/5 * * * *')])])


node {
    stage("Terraform Pipeline"){
        checkout([
            $class: 'GitSCM', branches: [[name: '*/master']], extensions: [], 
            userRemoteConfigs: [[url: 'https://github.com/jiro1/vpc-jenkins-job.git']]])
    }

    stage("Format"){
		timestamps {
            ws("workspace/vpc-jenkins-job/vpc"){
                sh "make f"
            }
    }
}

    stage("Cleaning"){
          timestamps {
            ws("workspace/vpc-jenkins-job/vpc"){
                sh "make c"
            }
    }
    }
	stage("Initialize"){
		timestamps {
            ws("workspace/vpc-jenkins-job/vpc"){
                sh "make i"
            }
    }
}

    stage("Plan"){
        timestamps {
            ws("workspace/vpc-jenkins-job/vpc"){
                sh "make p"
            }
    }
    }

    stage("Apply Pipeline"){
          timestamps {
            ws("workspace/vpc-jenkins-job/vpc"){
                sh "make a"
            }
    }
    }



    stage("Send Notifications to Slack"){
		slackSend color: '#BADA55', message: 'Hello, World!'
	}
}

    //stage("Script"){
	// 	sh label: '', script: 
	// 	'''#!/bin/bash
	// 		if [ ! -d /tmp/foo.txt ]; 
	// 		then
	// 			echo "Folder not found!"
	// 			echo "Creating a folder"
	// 			mkdir -p "/tmp/foo.txt" 
	// 		fi
	// 	'''
	// }

    // stage("Sleep"){
    //     sleep 60
    // }

    // stage("Intentionally Failed"){
    // 		error 'failed'
    // }

    // 	stage("Call Another Job"){
    // 		build "Packer"
    // }

    // stage("Send Email to Support"){
	// 	mail bcc: '', body: 'Running', cc: 'support@company.com', from: '', replyTo: '', subject: 'Test', to: 'farrukhsadykov@gmail.com'
	// }