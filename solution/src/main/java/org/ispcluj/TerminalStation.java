package org.ispcluj;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public class TerminalStation {
    private static final int MAX_CAPACITY = 4;
    private final Queue<Train> terminalBuffer = new ArrayDeque<>();
    private final Set<String> registeredIds = new HashSet<>();
    private boolean accepting = true;

    public synchronized boolean enqueueTrain(Train train) {
        if (train == null) {
            return false;
        }

        while (terminalBuffer.size() >= MAX_CAPACITY && accepting) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        }

        if (!accepting) {
            return false;
        }

        String id = train.id();
        if (id != null && registeredIds.contains(id)) {
            return false;
        }

        if (id != null) {
            registeredIds.add(id);
        }

        terminalBuffer.add(train);
        notifyAll();
        return true;
    }

    public synchronized Train dequeueNextTrain(PlatformTrack track) {
        while (terminalBuffer.isEmpty()) {
            if (!accepting) {
                return null;
            }
            if (track != null) {
                track.updateState(PlatformState.BLOCKED);
            }
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }

        Train nextTrain = terminalBuffer.poll();
        notifyAll();
        return nextTrain;
    }

    public synchronized boolean isEmpty() {
        return terminalBuffer.isEmpty();
    }

    public synchronized void closeStation() {
        accepting = false;
        notifyAll();
    }

    public synchronized int getRegisteredCount() {
        return registeredIds.size();
    }

    public synchronized Set<String> getRegisteredIds() {
        return new HashSet<>(registeredIds);
    }

    public int getMaxCapacity() {
        return MAX_CAPACITY;
    }
}
