node {
    properties([pipelineTriggers([cron('* * * * *')])])
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
        echo "Hello"
    }

    stage("Send Email to Support"){
		mail bcc: '', body: 'Running', cc: 'support@company.com', from: '', replyTo: '', subject: 'Test', to: 'farrukhsadykov@gmail.com'
	}
}