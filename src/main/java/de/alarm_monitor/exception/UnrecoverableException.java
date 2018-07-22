package de.alarm_monitor.exception;

class UnrecoverableException extends Exception {
    public UnrecoverableException() {
    }

    public UnrecoverableException(final String message) {
        super(message);
    }

    public UnrecoverableException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UnrecoverableException(final Throwable cause) {
        super(cause);
    }

    public UnrecoverableException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
