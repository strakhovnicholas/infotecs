package com.straxov.agent;

import com.straxov.logger.Logger;
import com.straxov.city.City;
import com.straxov.entitiy.Bank;
import com.straxov.entitiy.Spender;
import com.straxov.entitiy.Worker;

/**
 * Media is a daemon thread that periodically prints and logs the state of the city (banks, workers, spenders).
 */
public class Media extends Thread {
    private final int interval;
    private volatile boolean running = true;

    /**
     * Constructs a Media thread with the specified interval.
     * @param interval interval in milliseconds between reports
     */
    public Media(int interval) {
        this.interval = interval;
        setDaemon(true);
    }

    /**
     * Stops the Media thread.
     */
    public void stopMedia() {
        running = false;
    }

    /**
     * Main loop of the Media thread.
     */
    @Override
    public void run() {
        while (running) {
            try {
                printCityState();
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    /**
     * Prints and logs the current state of the city.
     */
    private void printCityState() {
        City city = City.getInstance();
        int total = 0;
        StringBuilder sb = new StringBuilder();

        sb.append("Good news for everyone! Total amount money in city is: ")
                .append(getTotalMoney(city))
                .append("$\n");

        for (Bank bank : city.getBanks()) {
            sb.append("This ")
                    .append(bank.getName())
                    .append(" has money: ")
                    .append(bank.getMoney().get())
                    .append("$\n");
        }

        for (Worker worker : city.getWorkers()) {
            sb.append("This ")
                    .append(worker.getName())
                    .append(" has money: ")
                    .append(worker.getMoney())
                    .append("$\n");
        }

        for (Spender spender : city.getSpenders()) {
            sb.append("This ")
                    .append(spender.getName())
                    .append(" has money: ")
                    .append(spender.getMoney())
                    .append("$\n");
        }

        String report = sb.toString();
        System.out.print(report);
        Logger.log("Media summary:\n" + report);
    }

    /**
     * Calculates the total amount of money in the city.
     * @return total money
     */
    private int getTotalMoney(City city) {
        int total = 0;
        for (Bank bank : city.getBanks()) total += bank.getMoney().get();
        for (Worker worker : city.getWorkers()) total += worker.getMoney();
        for (Spender spender : city.getSpenders()) total += spender.getMoney();
        return total;
    }
}