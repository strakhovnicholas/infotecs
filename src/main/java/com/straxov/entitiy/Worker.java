package com.straxov.entitiy;

import com.straxov.city.City;
import com.straxov.loader.ConfigLoader;
import com.straxov.logger.Logger;
import com.straxov.util.Utils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * The Worker class models a worker who can be hired by only one spender at a time.
 * The worker receives a salary, works, and when their savings reach a limit, deposits the money in a bank.
 */
public class Worker extends Client {
    private static final AtomicInteger idCounter = new AtomicInteger(1);
    private final int id;
    private final int salary;
    private final int moneyLimit;
    private volatile boolean busy = false;
    private final Object lock = new Object();

    /**
     * Constructs a new Worker.
     *
     * @param initialMoney initial amount of money
     * @param salary       salary amount
     * @param moneyLimit   savings limit for depositing to the bank
     */
    public Worker(int initialMoney, int salary, int moneyLimit) {
        super("Worker - " + idCounter.get(), initialMoney);
        this.id = idCounter.getAndIncrement();
        this.salary = salary;
        this.moneyLimit = moneyLimit;
    }

    /**
     * Gets the unique id of this worker.
     *
     * @return the worker's id
     */
    public int getId() {
        return id;
    }

    /**
     * Checks if the worker is currently busy (hired).
     *
     * @return true if busy, false otherwise
     */
    public boolean isBusy() {
        synchronized (lock) {
            return busy;
        }
    }

    /**
     * Sets the busy status of the worker.
     *
     * @param busy true if busy, false otherwise
     */
    public void setBusy(boolean busy) {
        synchronized (lock) {
            this.busy = busy;
            if (busy) {
                lock.notifyAll();
            }
        }
    }

    /**
     * Receives salary from a spender.
     *
     * @param amount the salary amount
     */
    public void receiveSalary(int amount) {
        addMoney(amount);
    }

    /**
     * The main loop of the worker's behavior.
     * The worker waits to be hired, works, and deposits money in the bank when the limit is reached.
     */
    @Override
    public void run() {
        while (running) {
            try {
                synchronized (lock) {
                    while (!busy && running) {
                        lock.wait();
                    }
                }
                if (!running) break;
                Thread.sleep(ConfigLoader.getWorkerWorkDuration());
                setBusy(false);
                if (getMoney() >= moneyLimit) {
                    boolean deposited = false;
                    while (!deposited && running) {
                        Bank bank = Utils.getRandom(City.getInstance().getBanks());
                        try {
                            Logger.log(name + " deposited accumulated money to bank " + bank.getName() + " in the amount of " + getMoney() + "$.");
                            bank.serve(this, false, getMoney());
                            deposited = true;
                        } catch (InterruptedException e) {
                            Thread.sleep(50);
                        }
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        System.out.println("Thread " + name + " has been stopped.");
    }
}
