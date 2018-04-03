package de.alarm_monitor.exception;

public class EMailSendException extends Exception {
    public EMailSendException() {
    }

    public EMailSendException(final String message) {
        super(message);
    }

    public EMailSendException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public EMailSendException(final Throwable cause) {
        super(cause);
    }

    public EMailSendException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
