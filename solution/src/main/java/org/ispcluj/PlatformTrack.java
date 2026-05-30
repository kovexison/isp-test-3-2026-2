package org.ispcluj;

public class PlatformTrack implements Runnable {
    private final String trackName;
    private final TerminalStation terminalStation;
    private volatile boolean running = true;
    private volatile PlatformState currentState = PlatformState.VACANT;

    public PlatformTrack(String trackName, TerminalStation terminalStation) {
        this.trackName = trackName;
        this.terminalStation = terminalStation;
    }

    @Override
    public void run() {
        while (running) {
            Train train = terminalStation.dequeueNextTrain(this);
            if (train == null) {
                break;
            }

            updateState(PlatformState.OCCUPIED);
            try {
                if ("Overload".equalsIgnoreCase(train.type())) {
                    throw new TerminalOverloadException("Terminal overload on " + trackName);
                }

                train.validateSignals();
                Thread.sleep(300);
            } catch (SignalFailureException e) {
                System.err.println("[SignalFailure][" + trackName + "] " + e.getMessage());
            } catch (TerminalOverloadException e) {
                System.err.println("[Overload][" + trackName + "] " + e.getMessage());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } finally {
                updateState(PlatformState.VACANT);
            }
        }

        updateState(PlatformState.VACANT);
    }

    public void updateState(PlatformState state) {
        this.currentState = state;
    }

    public PlatformState getCurrentState() {
        return currentState;
    }

    public void shutdown() {
        running = false;
    }
}
