#!/usr/bin/env zsh
#   Deploying to GCP
#      This script uploads jar for Cloud build then creates a  PubSub function that can be linked to
#      the Cloud scheduler
export PROJECT_HOME ="/Users/freda/MongoHackathon2022"
echo $PROJECT_HOME
cd /Users/freda/MongoHackathon2022
gcloud functions deploy fn-gdelt-update  --entry-point hackathon.PubSub --runtime java11 --memory 1024MB --trigger-topic hackathon --source build/libs