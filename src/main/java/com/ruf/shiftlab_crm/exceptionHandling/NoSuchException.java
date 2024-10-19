package com.ruf.shiftlab_crm.exceptionHandling;

public class NoSuchException extends RuntimeException {
    public NoSuchException(String message) {
        super(message);
    }
}
