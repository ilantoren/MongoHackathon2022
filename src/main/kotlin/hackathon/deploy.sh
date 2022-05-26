#!/usr/bin/env zsh
#   Deploying to GCP
#      This script uploads jar for Cloud build then creates a  PubSub taoke that can be linked to
#      the Cloud scheduler

cd $PROJECT_HOME
gcloud functions deploy fn-gdelt-update  --entry-point hackathon.PubSub --runtime java11 --memory 512MB --trigger-topic hackathon --source build/libs