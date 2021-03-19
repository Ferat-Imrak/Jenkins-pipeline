properties([
    buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '5', numToKeepStr: '5')), 
    disableConcurrentBuilds(), parameters([
    choice(choices: ['a', 'd'], description: 'Apply_Delete', name: 'ACTION'), 
    choice(choices: ['dev', 'qa', 'stage', 'prod'], description: 'Which Environment? ', name: 'ENVIRONMENT')]), 
    ])


node {
    stage("Terraform Pipeline"){
        checkout([
            $class: 'GitSCM', branches: [[name: '*/master']], extensions: [], 
            userRemoteConfigs: [[url: 'https://github.com/jiro1/vpc-jenkins-job.git']]])
    }

    stage("Format"){
		timestamps {
            ws("workspace/vpc-jenkins-job/eks"){
                sh "make f"
                echo "${ACTION}"
            }
    }
}

    stage("Cleaning"){
          timestamps {
            ws("workspace/vpc-jenkins-job/eks"){
                sh "make c"
            }
    }
    }

	stage("Initialize"){
		timestamps {
            ws("workspace/vpc-jenkins-job/eks"){
                sh "make i"
            }
    }
}

    stage("Plan"){
        timestamps {
            ws("workspace/vpc-jenkins-job/eks"){
                sh "make p"
            }
    }
    }

    stage("Apply Pipeline"){
          timestamps {
            ws("workspace/vpc-jenkins-job/eks"){
                sh "make ${ACTION}"
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