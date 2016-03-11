package ro.fortsoft.elk.testdata.generator;

import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.person.Person;
import net.logstash.logback.marker.Markers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author sbalamaci
 */
public class LoginEvent implements Runnable {

    private static final

    int minWaitMs = 500;
    int maxWaitMs = 2500;

    private static final Logger log = LoggerFactory.getLogger(LoginEvent.class);

    private static final String ipNetworkPattern = "192.168.0.";
    private static final int MIN_VALID_LOGIN_IP = 100;

    private int randomIp;

    public LoginEvent() {
        randomIp = ThreadLocalRandom.current().nextInt(0, 192);
    }

    private void waitBeforeStart() {
        int waitMs = ThreadLocalRandom.current().nextInt(minWaitMs, maxWaitMs);
        try {
            Thread.sleep(waitMs);
        } catch (InterruptedException ignored) {  }
    }

    @Override
    public void run() {
        waitBeforeStart();

        Marker ipMarker = Markers.append("remoteIP", ipNetworkPattern + randomIp);
        String username = randomUsername();

        if(randomIp < MIN_VALID_LOGIN_IP) {
            log.info(ipMarker, "SUCCESS login for user='{}'", username);
        } else {
            log.info(ipMarker, "FAILED login for user='{}'", username);
        }
    }

    private String randomUsername() {
        Fairy fairy = Fairy.create();
        Person person = fairy.person();
        return person.email();
    }

}
