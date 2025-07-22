package com.straxov.entitiy;

/**
 * Factory interface for creating simulation entities (Banks, Workers, and Spenders).
 * Provides creation methods for all major entity types in the economic simulation.
 */
public interface EntityFactory {

    /**
     * Creates a new Bank instance with specified initial capital.
     *
     * @param initialMoney The starting amount of money for the bank
     * @return A new Bank instance
     */
    Bank createBank(int initialMoney);

    /**
     * Creates a new Worker instance with specified financial parameters.
     *
     * @param initialMoney The worker's starting money amount
     * @param salary       The worker's regular salary amount
     * @param moneyLimit   The threshold at which worker deposits money to bank
     * @return A new Worker instance
     */
    Worker createWorker(int initialMoney, int salary, int moneyLimit);

    /**
     * Creates a new Spender instance with specified initial money.
     *
     * @param initialMoney The spender's starting money amount
     * @return A new Spender instance
     */
    Spender createSpender(int initialMoney);
}