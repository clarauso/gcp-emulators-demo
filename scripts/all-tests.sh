#!/bin/sh

echo "Running unit tests"
./mvnw clean test -DskipUTs=false || exit

echo "Running integration tests"
cd firebase-emulators && \
  firebase emulators:exec 'sh init-pubsub.sh && cd ../ && ./mvnw verify -DskipUTs=true -DskipITs=false' --project local-project || exit