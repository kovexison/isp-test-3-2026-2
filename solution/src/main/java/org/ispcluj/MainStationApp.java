package org.ispcluj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MainStationApp {
    @FunctionalInterface
    interface MenuAction {
        void execute() throws Exception;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<Integer, MenuAction> menuActions = new HashMap<>();

        List<Train> structuralSchedule = populateSampleSchedule();
        TerminalStation[] stationHolder = new TerminalStation[]{new TerminalStation()};

        menuActions.put(1, () -> {
            System.out.println("\n[Action] Starting Live Train Platform Allocation Simulation...");
            TerminalStation terminalStation = new TerminalStation();
            stationHolder[0] = terminalStation;

            List<Train> filteredSchedule = structuralSchedule.stream()
                    .filter(train -> !"Freight".equalsIgnoreCase(train.type()))
                    .toList();

            TrafficController controller = new TrafficController(terminalStation, filteredSchedule);
            PlatformTrack trackOne = new PlatformTrack("Track-1", terminalStation);
            PlatformTrack trackTwo = new PlatformTrack("Track-2", terminalStation);

            Thread controllerThread = new Thread(controller, "TrafficController");
            Thread trackThreadOne = new Thread(trackOne, "PlatformTrack-1");
            Thread trackThreadTwo = new Thread(trackTwo, "PlatformTrack-2");

            controllerThread.start();
            trackThreadOne.start();
            trackThreadTwo.start();

            controllerThread.join();
            terminalStation.closeStation();

            trackOne.shutdown();
            trackTwo.shutdown();
            trackThreadOne.join();
            trackThreadTwo.join();

            System.out.println("Simulation complete. Unique trains registered: " + terminalStation.getRegisteredCount());
        });

        menuActions.put(2, () -> {
            System.out.println("\n[Action] Triggering Controlled Custom Exception Test...");
            TerminalStation terminalStation = new TerminalStation();
            PlatformTrack exceptionTrack = new PlatformTrack("Exception-Track", terminalStation);
            Thread exceptionThread = new Thread(exceptionTrack, "Exception-Track");

            exceptionThread.start();
            terminalStation.enqueueTrain(new Train("S-88", "InterCity", null));
            terminalStation.enqueueTrain(new Train("OL-1", "Overload", "Bucharest"));
            terminalStation.closeStation();
            exceptionTrack.shutdown();
            exceptionThread.join();

            System.out.println("Exception test completed.");
        });

        menuActions.put(3, () -> {
            TerminalStation terminalStation = stationHolder[0];
            if (terminalStation == null) {
                System.out.println("No simulation has been run yet.");
                return;
            }

            System.out.println("\n[Action] Displaying Processed Train Summary...");
            System.out.println("Unique trains registered: " + terminalStation.getRegisteredCount());
            System.out.println("Registered IDs: " + terminalStation.getRegisteredIds());
        });

        while (true) {
            printMenu();
            System.out.print("Select an option (1-4): ");

            try {
                if (!scanner.hasNextInt()) {
                    System.out.println("Invalid input! Please enter a numerical choice.");
                    scanner.next();
                    continue;
                }

                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                    case 2:
                    case 3:
                        menuActions.get(choice).execute();
                        break;
                    case 4:
                        System.out.println("Exiting terminal control system. Safe travels!");
                        scanner.close();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Error: Option out of bounds! Choose a valid menu entry.");
                        break;
                }
            } catch (Exception e) {
                System.err.println("[System Error Alert] An unhandled exception escaped to the menu context: " + e.getMessage());
            }

            System.out.println("\n========================================================");
        }
    }

    private static void printMenu() {
        System.out.println("\n=== Central Station Platform Allocation Console ===");
        System.out.println("1. Run Real-time Allocation Engine");
        System.out.println("2. Simulate & Trigger Signal Exceptions Check");
        System.out.println("3. View Total Processed Trains / Unique Registry Stats");
        System.out.println("4. Shutdown & Terminate Station Program");
    }

    private static List<Train> populateSampleSchedule() {
        return List.of(
                new Train("IC-120", "InterCity", "Cluj-Napoca"),
                new Train("FR-441", "Freight", "Arad"),
                new Train("IR-33", "Regional", "Sibiu"),
                new Train("IC-120", "InterCity", "Cluj-Napoca"),
                new Train("S-88", "InterCity", null)
        );
    }
}
