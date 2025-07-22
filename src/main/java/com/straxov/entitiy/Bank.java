package com.straxov.entitiy;

import com.straxov.logger.Logger;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * The Bank class simulates a bank that can serve only one client at a time.
 * The bank can issue loans and accept deposits.
 */
public class Bank implements Runnable {
    private static final AtomicInteger idCounter = new AtomicInteger(1);
    private final int id;
    private final String name;
    private final AtomicInteger money;
    private volatile boolean running = true;
    private boolean busy = false;
    private final Object lock = new Object();

    /**
     * Creates a new bank with initial amount of money.
     *
     * @param initialMoney the initial amount of money
     */
    public Bank(int initialMoney) {
        this.id = idCounter.getAndIncrement();
        this.name = "Bank - " + id;
        this.money = new AtomicInteger(initialMoney);
    }

    /**
     * Returns the bank's name.
     *
     * @return the bank's name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the AtomicInteger representing the bank's money.
     *
     * @return the bank's money
     */
    public AtomicInteger getMoney() {
        return money;
    }

    /**
     * Stops the bank's operation.
     */
    public void stopBank() {
        running = false;
        synchronized (lock) {
            lock.notifyAll();
        }
    }

    /**
     * Serves a client: issues a loan or accepts a deposit.
     *
     * @param client   the client to serve
     * @param isCredit true if it's a loan; false if it's a deposit
     * @param amount   the transaction amount
     * @throws InterruptedException if the thread is interrupted
     */
    public void serve(Client client, boolean isCredit, int amount) throws InterruptedException {
        synchronized (lock) {
            while (busy && running) {
                lock.wait();
            }
            if (!running) return;
            busy = true;
            if (isCredit) {
                client.addMoney(amount);
                money.addAndGet(-amount);
                Logger.log(name + " issued a loan of " + amount + "$ to client " + client.getName() + ".");
            } else {
                client.removeMoney(amount);
                money.addAndGet(amount);
                Logger.log(name + " accepted a deposit of " + amount + "$ from client " + client.getName() + ".");
            }
            busy = false;
            lock.notifyAll();
        }
    }

    /**
     * The main operation loop of the bank.
     */
    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                break;
            }
        }
        System.out.println("Thread " + name + " has been stopped.");
    }
}