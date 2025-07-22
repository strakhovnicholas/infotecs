package com.straxov.city;

import com.straxov.loader.ConfigLoader;
import com.straxov.entitiy.Bank;
import com.straxov.entitiy.EntityFactory;
import com.straxov.entitiy.Spender;
import com.straxov.entitiy.Worker;

/**
 * CityFactory is a concrete implementation of the EntityFactory interface.
 * It is responsible for creating instances of Bank, Worker, and Spender for the city simulation.
 */
public class СityFactory implements EntityFactory {
    /**
     * Creates a new Bank with the specified initial amount of money.
     *
     * @param initialMoney the initial amount of money for the bank
     * @return a new Bank instance
     */
    @Override
    public Bank createBank(int initialMoney) {
        return new Bank(initialMoney);
    }

    /**
     * Creates a new Worker with the specified parameters.
     *
     * @param initialMoney the initial amount of money for the worker
     * @param salary       the salary amount for the worker
     * @param moneyLimit   the savings limit for the worker to deposit in the bank
     * @return a new Worker instance
     */
    @Override
    public Worker createWorker(int initialMoney, int salary, int moneyLimit) {
        return new Worker(initialMoney, salary, moneyLimit);
    }

    /**
     * Creates a new Spender with the specified initial money and salary from config.
     *
     * @param initialMoney the initial amount of money for the spender
     * @return a new Spender instance
     */
    @Override
    public Spender createSpender(int initialMoney) {
        int salary = ConfigLoader.getWorkerSalary();
        return new Spender(initialMoney, salary);
    }
}