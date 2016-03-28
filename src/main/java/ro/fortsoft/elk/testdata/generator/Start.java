package ro.fortsoft.elk.testdata.generator;

import ch.qos.logback.classic.LoggerContext;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.fortsoft.elk.testdata.generator.event.builder.EventBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author sbalamaci
 */
public class Start {

    private static final Logger log = LoggerFactory.getLogger(Start.class);

    private static final Config config = ConfigFactory.load("./jobs");

    public static void main(String[] args) {
        EventBuilder eventBuilder = new EventBuilder(config);

        int numberOfEvents = getNumberOfEvents();
        int numberOfConcurrentThreads = getNumberOfConcurrentThreads();
        log.info("Generating {} events", numberOfEvents);

        /*The newFixedThreadPool method creates a fixed thread executor,
          However it also takes as parameter an unbounded(MAX_INT) - LinkedBlockingQueue
          which means the ExecutorService can receive quickly(not blocking) new jobs
          which are held "in store" until one of the worker threads gets freed
        */
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfConcurrentThreads);

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
    }


    /**
     * We signal the async threads that push the data to Logstash to stop
     * and the Logstash server connection to be closed
     */
    private static void shutdownLogger() {
        log.info("Shutting down logger");
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        loggerContext.stop();
    }

    private static int getNumberOfEvents() {
        return config.getInt("events.number");
    }

    private static int getNumberOfConcurrentThreads() {
        return config.getInt("events.threads");
    }

}
