package com.straxov.logger;

/**
 * Utility class for logging messages to the console.
 */
public class Logger {
    /**
     * Logs the specified message to the console with a "Logging:" prefix.
     *
     * @param message the message to be logged
     */
    public static void log(String message) {
        System.out.println("Logging: " + message);
    }
}