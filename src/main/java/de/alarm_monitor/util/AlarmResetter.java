package de.alarm_monitor.util;


import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.alarm_monitor.main.Start;
import org.apache.commons.lang.time.FastDateFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

@Singleton
public class AlarmResetter {

    private static final Logger logger = LogManager.getLogger(AlarmResetter.class);

    private long resetTime = Long.MAX_VALUE;


    @Inject
    public AlarmResetter() {
        startController();
    }


    public void resetAlarm(final long delay) {

        resetTime = System.currentTimeMillis() + delay * 1000 * 60;
        final FastDateFormat format = FastDateFormat.getInstance();
        logger.info("Dieplay wird um {} zurückgesetzt", format.format(resetTime));
    }

    private void startController() {
        final Runnable controller = () -> {
            while (true) {
                if (resetTime < System.currentTimeMillis()) {
                    Start.getDisplay().resetAlarm();
                    logger.info("resetting Display");
                    resetTime = Long.MAX_VALUE;
                }

                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (final InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        final Thread controllerThread = new Thread(controller);
        controllerThread.start();
    }
}
