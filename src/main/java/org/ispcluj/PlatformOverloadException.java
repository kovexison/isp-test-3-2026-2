package org.ispcluj;

//unchecked exception
//TODO: REQ-7
public class PlatformOverloadException extends RuntimeException {
    public PlatformOverloadException(String message) {
        super(message);
    }
}
