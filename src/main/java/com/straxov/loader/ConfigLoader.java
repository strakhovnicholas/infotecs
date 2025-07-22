package com.straxov.loader;

import java.io.*;
import java.util.Properties;

/**
 * Singleton configuration loader that reads simulation parameters from properties files.
 * Provides thread-safe access to all configuration parameters.
 */
public class ConfigLoader {
    private static final Properties properties = new Properties();
    private static final ConfigLoader INSTANCE = new ConfigLoader();

    /**
     * Private constructor to prevent instantiation.
     */
    private ConfigLoader() {
    }

    /**
     * Returns the singleton instance of ConfigLoader.
     *
     * @return The single instance of ConfigLoader
     */
    public static ConfigLoader getInstance() {
        return INSTANCE;
    }

    /**
     * Loads configuration parameters from a properties file.
     *
     * @param fileName Path to the properties file
     * @throws RuntimeException if the file cannot be read
     */
    public synchronized void loadFromPropertiesFile(String fileName) {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(in);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration file: " + fileName, e);
        }
    }


    /**
     * Gets the duration of a simulation day in milliseconds.
     *
     * @return Day duration in ms
     */
    public static long getDayDuration() {
        return Long.parseLong(properties.getProperty("day.duration"));
    }

    /**
     * Gets the duration of worker's work period in milliseconds.
     *
     * @return Work duration in ms
     */
    public static long getWorkerWorkDuration() {
        return Long.parseLong(properties.getProperty("worker.work.duration"));
    }

    /**
     * Gets the duration of lunch break in milliseconds.
     *
     * @return Lunch duration in ms
     */
    public static long getLunchDuration() {
        return Long.parseLong(properties.getProperty("lunch.duration"));
    }

    /**
     * Gets the number of banks in the simulation.
     *
     * @return Number of banks
     */
    public static int getBanksCount() {
        return Integer.parseInt(properties.getProperty("banks.count"));
    }

    /**
     * Gets the number of workers in the simulation.
     *
     * @return Number of workers
     */
    public static int getWorkersCount() {
        return Integer.parseInt(properties.getProperty("workers.count"));
    }

    /**
     * Gets the number of spenders in the simulation.
     *
     * @return Number of spenders
     */
    public static int getSpendersCount() {
        return Integer.parseInt(properties.getProperty("spenders.count"));
    }

    /**
     * Gets the initial money amount for each bank.
     *
     * @return Initial bank money
     */
    public static int getBankInitialMoney() {
        return Integer.parseInt(properties.getProperty("bank.initial.money"));
    }

    /**
     * Gets the initial money amount for each client.
     *
     * @return Initial client money
     */
    public static int getClientInitialMoney() {
        return Integer.parseInt(properties.getProperty("client.initial.money"));
    }

    /**
     * Gets the salary amount workers receive.
     *
     * @return Worker salary amount
     */
    public static int getWorkerSalary() {
        return Integer.parseInt(properties.getProperty("worker.salary"));
    }

    /**
     * Gets the money limit at which workers deposit to banks.
     *
     * @return Worker money limit
     */
    public static int getWorkerMoneyLimit() {
        return Integer.parseInt(properties.getProperty("worker.money.limit"));
    }
}