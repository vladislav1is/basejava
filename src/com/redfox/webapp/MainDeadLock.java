package com.redfox.webapp;

public class MainDeadLock {
    private static final String lock1 = "lock1";
    private static final String lock2 = "lock2";

    public static void main(String[] args) {
        holder(lock1, lock2);
        holder(lock2, lock1);
    }

    static void holder(String lock1, String lock2) {
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " Waiting " + lock1);
            synchronized (lock1) {
                System.out.println(Thread.currentThread().getName() + " Holding " + lock1);
                Thread.yield();
                System.out.println(Thread.currentThread().getName() + " Waiting " + lock2);
                synchronized (lock2) {
                    System.out.println(Thread.currentThread().getName() + " Holding " + lock2);
                }
            }
        }).start();
    }
}
