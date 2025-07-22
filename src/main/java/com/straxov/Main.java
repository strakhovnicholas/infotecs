package com.straxov;

import com.straxov.agent.Media;
import com.straxov.city.City;
import com.straxov.entitiy.Spender;
import com.straxov.entitiy.Worker;
import com.straxov.loader.ConfigLoader;
import com.straxov.helper.HelpDesk;

import java.util.ArrayList;
import java.util.List;

/**
 * Main class for running the city economic simulation.
 * Initializes all components, starts worker/spender threads, and manages simulation lifecycle.
 */
public class Main {
    /**
     * Entry point for the city simulation application.
     *
     * @param args Command line arguments (not used)
     * @throws Exception if simulation encounters errors
     */
    public static void main(String[] args) throws Exception {
        // HelpDesk activation
        HelpDesk helpDesk = HelpDesk.getInstance();
        helpDesk.printHelp();
        // Load configuration
        ConfigLoader config = ConfigLoader.getInstance();
        config.loadFromPropertiesFile("src/main/resources/config.properties");

        // Initialize city (all entities created inside City)
        City city = City.getInstance();

        // Calculate initial money
        int totalStart = city.getTotalMoney();
        System.out.println("Total money amount in city on day start: " + totalStart + "$\n");

        // Start all threads
        List<Thread> threads = getThreads(city);

        // Start media daemon thread
        Media media = new Media(2000);
        media.setDaemon(true);
        media.start();

        // Wait for day to complete
        Thread.sleep(ConfigLoader.getDayDuration());

        // Stop all client threads
        city.getWorkers().forEach(Worker::stopClient);
        city.getSpenders().forEach(Spender::stopClient);

        // Wait for threads to finish
        for (Thread thread : threads) {
            thread.join();
        }

        // Calculate final money
        int totalEnd = city.getTotalMoney();
        System.out.println("\nTotal money amount in city on day end: " + totalEnd + "$\n");
    }

    private static List<Thread> getThreads(City city) {
        List<Thread> threads = new ArrayList<>();

        // Start worker threads
        for (Worker worker : city.getWorkers()) {
            Thread workerThread = new Thread(worker, worker.getName());
            threads.add(workerThread);
            workerThread.start();
        }

        // Start spender threads
        for (Spender spender : city.getSpenders()) {
            Thread spenderThread = new Thread(spender, spender.getName());
            threads.add(spenderThread);
            spenderThread.start();
        }
        return threads;
    }
}