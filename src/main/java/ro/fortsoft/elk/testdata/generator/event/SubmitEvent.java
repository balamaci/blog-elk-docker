package ro.fortsoft.elk.testdata.generator.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author sbalamaci
 */
public class SubmitEvent extends BaseEvent {

    private static final Logger log = LoggerFactory.getLogger(LoginEvent.class);

    @Override
    public void doWork() {
        log.info("Submitting");
    }
}
