package com.straxov.entitiy;

/**
 * Abstract class representing a city client (either Worker or Spender).
 * Provides basic money management functionality and thread control mechanisms.
 * All money operations are thread-safe through synchronization.
 */
public abstract class Client implements Runnable {
    protected final String name;
    protected volatile int money;
    protected volatile boolean running = true;

    /**
     * Constructs a new Client with specified name and initial money amount.
     *
     * @param name         The client's identifier name
     * @param initialMoney Starting money amount for the client
     */
    public Client(String name, int initialMoney) {
        this.name = name;
        this.money = initialMoney;
    }

    /**
     * Gets the client's name.
     *
     * @return The client's name string
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the client's current money amount (thread-safe).
     *
     * @return Current money balance
     */
    public synchronized int getMoney() {
        return money;
    }

    /**
     * Adds money to the client's balance (thread-safe).
     *
     * @param amount Positive amount to add
     */
    public synchronized void addMoney(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        this.money += amount;
    }

    /**
     * Deducts money from the client's balance (thread-safe).
     *
     * @param amount Positive amount to deduct
     * @throws IllegalStateException if deduction would result in negative balance
     */
    public synchronized void removeMoney(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (this.money < amount) {
            throw new IllegalStateException("Insufficient funds");
        }
        this.money -= amount;
    }

    /**
     * Signals the client thread to stop execution.
     * Actual termination depends on the run() implementation.
     */
    public void stopClient() {
        running = false;
    }

    /**
     * Client behavior implementation (to be defined by subclasses).
     */
    @Override
    public abstract void run();
}