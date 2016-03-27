package ro.fortsoft.elk.testdata.generator;

import ro.fortsoft.elk.testdata.generator.event.LoginEvent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * @author sbalamaci
 */
public class Start {

    private static final int NUMBER_OF_EVENTS = 1000;
    private static final int CONCURRENT_THREADS = 10;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(CONCURRENT_THREADS);

        IntStream.of(NUMBER_OF_EVENTS)
                .mapToObj(Start::randomEvent)
                .forEach(executorService::submit);

        executorService.shutdown();
    }

    public static Runnable randomEvent(int val) {
        return new LoginEvent();
    }

}
