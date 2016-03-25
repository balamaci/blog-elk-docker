#!/usr/bin/env bash

docker run -it --link $1:logstash_host --rm --name generator -v ~/.m2/:/root/.m2 -v "$PWD":/usr/src/mymaven -w /usr/src/mymaven maven:3.3.3-jdk-8 mvn clean compile exec:java