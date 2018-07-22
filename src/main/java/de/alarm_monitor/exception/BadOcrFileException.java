package de.alarm_monitor.exception;

public class BadOcrFileException extends Exception {

    private static final long serialVersionUID = 8656571236792820060L;

    public BadOcrFileException() {
        super();
    }

    public BadOcrFileException(final String arg0, final Throwable arg1, final boolean arg2, final boolean arg3) {
        super(arg0, arg1, arg2, arg3);
    }

    public BadOcrFileException(final String arg0, final Throwable arg1) {
        super(arg0, arg1);
    }

    public BadOcrFileException(final String arg0) {
        super(arg0);
    }

    public BadOcrFileException(final Throwable arg0) {
        super(arg0);
    }


}
