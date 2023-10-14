#!/bin/sh

# create topic
curl --location --request PUT 'http://localhost:8085/v1/projects/local-project/topics/test-topic'
# create subscription
curl --location --request PUT 'http://localhost:8085/v1/projects/local-project/subscriptions/test-sub' \
--header 'Content-Type: application/json' \
--data '{
    "topic": "projects/local-project/topics/test-topic"
}'