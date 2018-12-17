package realtests;

import com.google.inject.Provider;
import de.alarm_monitor.configuration.MainConfiguration;
import de.alarm_monitor.security.AlertAdminReporter;

public class TestAlertAdminReporter extends AlertAdminReporter{

    TestAlertAdminReporter( Provider<MainConfiguration> provider) {
        super(null, provider, null);
    }


    @Override
    public void sendAlertToAdmin(String message, Throwable throwable) {
        //noop
    }

    @Override
    public void sendAlertToAdmin(String message) {
        //noop
    }
}
