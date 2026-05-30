package org.ispcluj;

import java.util.List;
import java.util.Random;

public class TrafficController implements Runnable {
    private final TerminalStation terminalStation;
    private final List<Train> rawSchedule;
    private final Random random = new Random();

    public TrafficController(TerminalStation terminalStation, List<Train> rawSchedule) {
        this.terminalStation = terminalStation;
        this.rawSchedule = rawSchedule;
    }

    @Override
    public void run() {
        int index = 0;
        while (index < rawSchedule.size()) {
            int batchSize = 1 + random.nextInt(2);
            int end = Math.min(index + batchSize, rawSchedule.size());
            List<Train> batch = rawSchedule.subList(index, end);

            for (Train train : batch) {
                boolean enqueued = terminalStation.enqueueTrain(train);
                if (!enqueued) {
                    System.out.println("[TrafficController] Skipping duplicate/rejected train: " + train.id());
                }
            }

            index = end;
            try {
                Thread.sleep(200 + random.nextInt(300));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        terminalStation.closeStation();
    }
}
