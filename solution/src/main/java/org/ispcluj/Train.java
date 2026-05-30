package org.ispcluj;

public record Train(String id, String type, String origin) {
    public void validateSignals() throws SignalFailureException {
        if (origin == null || origin.isBlank()) {
            throw new SignalFailureException("Unverified origin for train " + id);
        }
    }
}
