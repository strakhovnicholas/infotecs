package com.straxov.entitiy;

import com.straxov.city.City;
import com.straxov.logger.Logger;
import com.straxov.util.Utils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * The Spender class models a spender who hires workers and pays them a salary.
 * If the spender runs out of money, they take a loan from a bank.
 */
public class Spender extends Client {
    private static final AtomicInteger idCounter = new AtomicInteger(1);
    private final int id;
    private final int salary;
    private final Object lock = new Object();

    /**
     * Constructs a new Spender with the specified initial money and salary for workers.
     *
     * @param initialMoney the initial amount of money
     * @param salary       the salary to pay to a worker
     */
    public Spender(int initialMoney, int salary) {
        super("Spender - " + idCounter.get(), initialMoney);
        this.id = idCounter.getAndIncrement();
        this.salary = salary;
    }

    /**
     * Gets the unique id of this spender.
     *
     * @return the spender's id
     */
    public int getId() {
        return id;
    }

    /**
     * The main loop of the spender's behavior.
     * The spender looks for a free worker, pays them a salary, and if needed, takes a loan from a bank.
     */
    @Override
    public void run() {
        while (running) {
            try {
                Worker worker = null;
                while (worker == null && running) {
                    for (Worker w : City.getInstance().getWorkers()) {
                        if (!w.isBusy()) {
                            worker = w;
                            break;
                        }
                    }
                    if (worker == null) {
                        Thread.sleep(50);
                    }
                }
                if (!running) break;
                synchronized (worker) {
                    worker.setBusy(true);
                    if (getMoney() < salary) {
                        boolean credited = false;
                        while (!credited && running) {
                            Bank bank = Utils.getRandom(City.getInstance().getBanks());
                            try {
                                bank.serve(this, true, salary);
                                credited = true;
                            } catch (InterruptedException e) {
                                Thread.sleep(50);
                            }
                        }
                    }
                    removeMoney(salary);
                    worker.receiveSalary(salary);
                    Logger.log(name + " paid salary to worker " + worker.getName() + " in the amount of " + salary + "$.");
                    synchronized (worker) {
                        worker.notifyAll();
                    }
                }
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        System.out.println("Thread " + name + " has been stopped.");
    }
}