package ro.fortsoft.elk.testdata.generator.event.base;

import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.person.Person;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author sbalamaci
 */
public abstract class BaseEvent implements Runnable {

    private int minWaitMs = 500;
    private int maxWaitMs = 2500;


    public abstract void doWork();

    @Override
    public void run() {
        waitBeforeStart();
        doWork();
    }

    private void waitBeforeStart() {
        int waitMs = ThreadLocalRandom.current().nextInt(minWaitMs, maxWaitMs);
        try {
            Thread.sleep(waitMs);
        } catch (InterruptedException ignored) {  }
    }

    protected String randomUsername() {
        Fairy fairy = Fairy.create();
        Person person = fairy.person();
        return person.email();
    }

    public void setMinWaitMs(int minWaitMs) {
        this.minWaitMs = minWaitMs;
    }

    public void setMaxWaitMs(int maxWaitMs) {
        this.maxWaitMs = maxWaitMs;
    }
}
