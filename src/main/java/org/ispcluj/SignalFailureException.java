package org.ispcluj;

// checked exception
//TODO: REQ-7
public class SignalFailureException extends Exception {
    public SignalFailureException(String message) {
        super(message);
    }
}
