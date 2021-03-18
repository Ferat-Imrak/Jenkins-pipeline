node {
    properties([pipelineTriggers([cron('H/5 * * * *')])])
    stage("Stage1"){
        echo "Hello"
    }

    stage("Stage2"){
        echo "Hello"
    }

    stage("Stage3"){
        echo "Hello"
    }

    stage("Stage4"){
        timestamps {
             echo "Hello"
        }
       
    }

    stage("Send Notifications to Slack"){
		slackSend color: '#BADA55', message: 'Hello, World!'
	}


    stage("Send Email to Support"){
		mail bcc: '', body: 'Running', cc: 'support@company.com', from: '', replyTo: '', subject: 'Test', to: 'farrukhsadykov@gmail.com'
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