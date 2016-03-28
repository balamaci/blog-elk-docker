#!/usr/bin/env bash
if [ $# -eq 0 ]
  then
    echo "Usage: event-generate.sh logstash_container_name"
    exit 1
fi

docker run -it --link $1:logstash_host --rm --name event_generator -v ~/.m2/:/root/.m2 -v "$PWD":/usr/src/mymaven -w /usr/src/mymaven maven:3.3.3-jdk-8 mvn clean compile exec:java