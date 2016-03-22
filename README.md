## Java app monitoring with ELK Stack Blog Series

   - **[Java app monitoring with ELK - Part I - Logstash and Logback](https://balamaci.ro/java-app-monitoring-with-elk-logstash/)**
   - **[Java app monitoring with ELK - Part II- ElasticSearch](https://balamaci.ro/java-app-monitoring-with-elk-elastic-search/)**
   - Part 3 - coming soon

## Prerequisites
  - Install Docker and [Docker-compose](http://docs.docker.com/compose/install/) 
  - Clone this repository
````bash
$ git clone git@github.com:balamaci/blog-elk-docker
````

## Code run
To start the ELK stack
````bash
docker-compose up
````
will read the docker-compose.yml and start the ELK containers.

you can do
````bash
docker ps
```
to see the running containers
```
CONTAINER ID        IMAGE                   COMMAND                CREATED             STATUS              PORTS                                            NAMES
ab4b849bcb62        elkintrogithub_kibana   "/docker-entrypoint.   8 minutes ago       Up 39 seconds       0.0.0.0:5601->5601/tcp                           elkintrogithub_kibana_1          
3cd9a4e56841        logstash:latest         "/docker-entrypoint.   9 minutes ago       Up 40 seconds       0.0.0.0:5000->5000/tcp                           elkintrogithub_logstash_1        
8a199941f859        elasticsearch:latest    "/docker-entrypoint.   9 minutes ago       Up 40 seconds       0.0.0.0:9200->9200/tcp, 0.0.0.0:9300->9300/tcp   elkintrogithub_elasticsearch_1   
```

you can easily get the ip of any container - logstash, kibana, elasticsearch
````bash
docker inspect --format '{{ .NetworkSettings.IPAddress }}' container_id
```


