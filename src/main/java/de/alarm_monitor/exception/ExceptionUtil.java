package de.alarm_monitor.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExceptionUtil {

    public static void logException(final Class clazz, final String msg, final Exception e) {
        final Logger logger = LogManager.getLogger(clazz);
        logger.error(msg);
        logger.trace("Exception was:", e);
    }


}
