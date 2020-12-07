package com.redfox.webapp;

public class DeadLockMain {
    private static final String lock1 = "lock1";
    private static final String lock2 = "lock2";

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            while (true) {
                holder(lock1, lock2);
            }
        });
        Thread t2 = new Thread(() -> {
            while (true) {
                holder(lock2, lock1);
            }
        });

        t1.start();
        t2.start();
    }

    static void holder(String lock1, String lock2) {
        System.out.println(Thread.currentThread().getName() + " Waiting " + lock1);
        synchronized (lock1) {
            System.out.println(Thread.currentThread().getName() + " Holding " + lock1);
            System.out.println(Thread.currentThread().getName() + " Waiting " + lock2);
            synchronized (lock2) {
                System.out.println(Thread.currentThread().getName() + " Holding " + lock2);
            }
        }
    }
}
