properties(
		[parameters(
			[choice(choices: 
			[
				'golden_image', 
				'vault', 
				'elk', 
				'gitlab',
				'source_ami_filter'], 
		description: 'What would you like to build? ', 
		name: 'TOOL'),
            choice(choices: 
            ['a', 'd'], 
            description: 'Apply_Delete', 
            name: 'ACTION'),
            choice(choices: [
                'dev', 
                'qa', 
                'stage', 
                'prod'], 
            description: 'Which Environment? ', 
            name: 'ENVIRONMENT')]), 
			choice(choices: 
			[
			'us-east-1', 
			'us-east-2', 
			'us-west-1', 
			'us-west-2', 
			'eu-west-1', 
			'eu-west-2', 
			'eu-central-1'], 
		description: 'Where would you like to build? ', 
		name: 'REGION')])])
        



        
node {	
	stage("Checkout SCM"){
		timestamps {
			ws {
			echo "Slack"
			checkout([$class: 'GitSCM', branches: [[name: 'master']], 
            doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], 
            userRemoteConfigs: [[url: 'https://github.com/jiro1/packer.git']]])
		}
	}
}
	stage("Run Packer"){
		timestamps {
			ws {
				sh "packer version"
				sh "packer build --var region=${REGION} tools/${TOOL}.json"
		}
	}
}

    stage("Apply Pipeline"){
          timestamps {
            ws("workspace/vpc-jenkins-job/vpc"){
                sh "make ${ACTION}"
            }
    }
}
	stage("Send slack notifications"){
		timestamps {
			ws {
				echo "Slack"
				//slackSend color: '#BADA55', message: 'Hello, World!'
			}
		}
	}
}