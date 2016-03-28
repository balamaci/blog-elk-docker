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

for event generation you can use provided script **event-generate.sh** with the container name from above
````bash
./event-generate.sh elkintrogithub_logstash_1
```

The number and type of events and is configured in the **jobs.conf** file:
 ```
 events {
     number:100,
     threads:10,
     jobs:[
         {
             name:login, //defined bellow
             probability:0.7
         },
         {
             name:submit,
             probability:0.3
         }
     ]
 
 }
 
 login {
    class : ro.fortsoft.elk.testdata.generator.event.LoginEvent
 }
 
 submit {
    class : ro.fortsoft.elk.testdata.generator.event.SubmitOrderEvent
 }
 ```
you can create and add your own event by extending and adding it to the list. 


````java
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfConcurrentThreads);

        /** From 0->numberOfEvents we produce an Event(extends Runnable)
            which we submit 
         **/
        IntStream.rangeClosed(0, numberOfEvents)
                .mapToObj(eventBuilder::randomEvent)
                .forEach(executorService::submit);

        //since all the jobs have been submitted we notify the pool that it can shutdown
        executorService.shutdown();

        try {
            executorService.awaitTermination(5, TimeUnit.MINUTES);
        } catch (InterruptedException ignored) {
        } finally {
            shutdownLogger();
        }
```

The **executorService** from Executors.newFixedThreadPool method which creates an ExecutorService with a pool of threads, but also 
as parameter an unbounded(MAX_INT) - **LinkedBlockingQueue**-.
This means the ExecutorService can receive quickly(not blocking since the **BlockingQueue** is unbounded) new jobs which are held "in store" until one of the worker threads gets freed.
```
    IntStream.rangeClosed(0, numberOfEvents)
                .mapToObj(eventBuilder::randomEvent)
                .forEach(executorService::submit);                 
```

since all the jobs have been submitted we notify the pool that it can shutdown so the Main thread can eventually exit
````java
    executorService.shutdown();
````

but we need to wait for the jobs that were submitted and not yet processed - those stored in the **BlockingQueue**
````java
    executorService.awaitTermination(5, TimeUnit.MINUTES);
````

The **shutdownLogger** command is necessary to stop the async threads which are pushing the log events into Logstash and to close the connection 