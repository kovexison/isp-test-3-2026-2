package org.ispcluj;

//unchecked exception
//TODO: REQ-7
public class TerminalOverloadException extends RuntimeException {
    public TerminalOverloadException(String message) {
        super(message);
    }
}
