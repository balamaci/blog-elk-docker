package ro.fortsoft.elk.testdata.generator.config;

/**
 * Hold
 *
 * @author sbalamaci
 */
public class JobInfo {

    private Class jobClass;

    public JobInfo(Class jobClass) {
        this.jobClass = jobClass;
    }

    public Class getJobClass() {
        return jobClass;
    }
}
