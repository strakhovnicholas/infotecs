package com.straxov.helper;

/**
 * Singleton help desk service providing assistance information for the city simulation.
 * Implements thread-safe singleton pattern using double-checked locking.
 */
public class HelpDesk {
    private static volatile HelpDesk instance;

    /**
     * Private constructor to prevent external instantiation.
     */
    private HelpDesk() {
    }

    /**
     * Returns the singleton instance of HelpDesk.
     * Uses double-checked locking pattern for thread safety.
     *
     * @return The single instance of HelpDesk
     */
    public static HelpDesk getInstance() {
        if (instance == null) {
            synchronized (HelpDesk.class) {
                if (instance == null) {
                    instance = new HelpDesk();
                }
            }
        }
        return instance;
    }

    /**
     * Displays basic help information about the city simulation.
     * Prints usage instructions to standard output.
     */
    public void printHelp() {
        System.out.println("--- City Simulation HelpDesk ---");
        System.out.println("This simulation models a city with banks, workers, and spenders.");
        System.out.println("- Workers can be hired by spenders and deposit money in banks.");
        System.out.println("- Spenders hire workers and pay them salaries. If they run out of money, they take loans from banks.");
        System.out.println("- Banks can only serve one client at a time and can both give loans and accept deposits.");
        System.out.println("- Media periodically reports the state of the city.");
        System.out.println("All configuration parameters can be set in the config.properties file.");
        System.out.println("--------------------------------");
    }
}