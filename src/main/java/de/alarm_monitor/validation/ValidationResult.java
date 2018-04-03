package de.alarm_monitor.validation;

class ValidationResult {


    private final ValidationCode code;
    private final String msg;


    public ValidationResult(final ValidationCode code, final String msg) {
        this.code = code;
        this.msg = msg;
    }


    public ValidationCode getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }


}

