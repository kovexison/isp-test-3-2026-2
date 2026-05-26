package org.ispcluj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

//TIP: To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

//TODO: implements REQ-2 (Java Streams pipeline execution with a Lambda expression) and
// REQ-8 (Interactive terminal switch-case wrapper execution loop).
public class MainStationApp {
    // Functional Interface for handling distinct interactive terminal actions
    @FunctionalInterface
    interface MenuAction {
        void execute() throws Exception;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<Integer, MenuAction> menuActions = new HashMap<>();

        // --- TODO: Initialize your Shared Resources here ---
        // Example: PlatformQueue queue = new PlatformQueue();
        // List<TrainService> structuralManifest = populateSampleSchedule();
        // ------------------------------------------------------------

        // Registering menu options using Lambdas and Functional Interfaces
        menuActions.put(1, () -> {
            System.out.println("\n[Action] Starting Live Train Platform Allocation Simulation...");
            // TODO: REQ-2 & REQ-8: Use Streams/Lambdas to filter initial schedule payload
            // TODO: REQ-3 & REQ-4: Instantiate and start your independent execution threads
        });

        menuActions.put(2, () -> {
            System.out.println("\n[Action] Triggering Controlled Custom Exception Test...");
            // TODO: REQ-7: Forcefully simulate a scenario that raises your custom checked/unchecked exceptions
            // Demonstrate that your catch blocks handle the flow gracefully without crashing the app.
        });

        menuActions.put(3, () -> {
            System.out.println("\n[Action] Displaying Processed Train Summary...");
            // TODO: REQ-1 & REQ-8: Use your unique identification collection to showcase statistics/summary
        });

        // Main Interaction Loop
        while (true) {
            printMenu();
            System.out.print("Select an option (1-4): ");

            try {
                if (!scanner.hasNextInt()) {
                    System.out.println("Invalid input! Please enter a numerical choice.");
                    scanner.next(); // Clear invalid token
                    continue;
                }

                int choice = scanner.nextInt();

                // Switch-Case Structural Controller
                switch (choice) {
                    case 1:
                    case 2:
                    case 3:
                        // Execute the corresponding functional interface mapping safely
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
        System.out.println("2. Simulate & Trigger Route Exceptions Check");
        System.out.println("3. View Total Processed Trains / Unique Registry Stats");
        System.out.println("4. Shutdown & Terminate Station Program");
    }

    /**
     * Helper method to generate sample initial data for testing.
     * Students may use, modify, or extend this dataset.
     */
    private static List<TrainService> populateSampleSchedule() {
        return List.of(
                //TODO: this gives errors because TrainService class is not implemented

                // Format: Service ID, Passenger Count, Line Code
                new TrainService("IC-120", 420, "P2"),
                new TrainService("R-441", 720, "P5"), // Should be filtered out via Streams (>600 passengers)
                new TrainService("IR-33", 180, "P1"),
                new TrainService("IC-120", 310, "P2"), // Duplicate ID test case
                new TrainService("S-88", 260, null)     // Edge case for checked exception test
        );
    }
}
