package de.alarm_monitor.exception;

class PngParserException extends Exception {

    public PngParserException() {
    }

    public PngParserException(final String message) {
        super(message);
    }

    public PngParserException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PngParserException(final Throwable cause) {
        super(cause);
    }

    public PngParserException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
