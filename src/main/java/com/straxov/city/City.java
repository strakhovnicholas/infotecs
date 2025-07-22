package com.straxov.city;

import com.straxov.loader.ConfigLoader;
import com.straxov.entitiy.Bank;
import com.straxov.entitiy.EntityFactory;
import com.straxov.entitiy.Spender;
import com.straxov.entitiy.Worker;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton class representing the city, containing all banks, workers, and spenders.
 * Responsible for initializing and providing access to all city entities.
 */
public class City {
    private static volatile City instance;
    private List<Bank> banks = new ArrayList<>();
    private List<Worker> workers = new ArrayList<>();
    private List<Spender> spenders = new ArrayList<>();
    private EntityFactory factory;

    /**
     * Private constructor initializes all city entities using configuration and factory.
     */
    private City() {
        factory = new СityFactory();
        for (int i = 0; i < ConfigLoader.getBanksCount(); i++) {
            banks.add(factory.createBank(ConfigLoader.getBankInitialMoney()));
        }
        for (int i = 0; i < ConfigLoader.getWorkersCount(); i++) {
            workers.add(factory.createWorker(
                    ConfigLoader.getClientInitialMoney(),
                    ConfigLoader.getWorkerSalary(),
                    ConfigLoader.getWorkerMoneyLimit()
            ));
        }
        for (int i = 0; i < ConfigLoader.getSpendersCount(); i++) {
            spenders.add(factory.createSpender(ConfigLoader.getClientInitialMoney()));
        }
    }

    /**
     * Returns the singleton instance of the city using double-checked locking pattern.
     *
     * @return the singleton City instance
     */
    public static City getInstance() {
        if (instance == null) {
            synchronized (City.class) {
                if (instance == null) {
                    instance = new City();
                }
            }
        }
        return instance;
    }

    /**
     * Gets the list of all banks in the city.
     *
     * @return unmodifiable list of banks
     */
    public List<Bank> getBanks() {
        return banks;
    }

    /**
     * Gets the list of all workers in the city.
     *
     * @return unmodifiable list of workers
     */
    public List<Worker> getWorkers() {
        return workers;
    }

    /**
     * Gets the list of all spenders in the city.
     *
     * @return unmodifiable list of spenders
     */
    public List<Spender> getSpenders() {
        return spenders;
    }

    /**
     * Calculates the total amount of money in circulation in the city.
     * Includes money from all banks, workers and spenders.
     *
     * @return total money amount in the city
     */
    public int getTotalMoney() {
        int total = 0;
        for (Bank bank : banks) {
            total += bank.getMoney().get();
        }
        for (Worker worker : workers) {
            total += worker.getMoney();
        }
        for (Spender spender : spenders) {
            total += spender.getMoney();
        }
        return total;
    }
}