package de.alarm_monitor.exception;

class PrintingException extends Exception {

    public PrintingException() {
    }

    public PrintingException(final String message) {
        super(message);
    }

    public PrintingException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PrintingException(final Throwable cause) {
        super(cause);
    }

    public PrintingException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
