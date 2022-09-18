package com.povorozniuk.pianomidilistener.exception;

public class MidiException extends Exception {

    Exception exceptionObj;

    public MidiException(final String exceptionMsg, final Exception exceptionObj) {
        super(exceptionMsg, exceptionObj);
        this.exceptionObj = exceptionObj;
    }

}
