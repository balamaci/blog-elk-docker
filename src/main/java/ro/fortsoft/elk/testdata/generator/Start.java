package ro.fortsoft.elk.testdata.generator;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author sbalamaci
 */
public class Start {

    private static final int NUMBER_OF_EVENTS = 1000;
    private static final int CONCURRENT_THREADS = 10;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(CONCURRENT_THREADS);

        for(int i=0; i < NUMBER_OF_EVENTS; i++) {
            executorService.submit(new LoginEvent());
        }

        executorService.shutdown();
    }

}
