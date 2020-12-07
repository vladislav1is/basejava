package com.redfox.webapp;

public class DeadLockMain {
    private static Object lock1 = new Object();
    private static Object lock2 = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            while (true) {
                synchronized (lock1) {
                    System.out.println("lock1: " + Thread.currentThread().getName());
                    synchronized (lock2) {
                        System.out.println("lock2: " + Thread.currentThread().getName());
                    }
                }
            }
        });
        Thread t2 = new Thread(() -> {
            while (true) {
                synchronized (lock2) {
                    System.out.println("lock2: " + Thread.currentThread().getName());
                    synchronized (lock1) {
                        System.out.println("lock1: " + Thread.currentThread().getName());
                    }
                }
            }
        });

        t1.start();
        t2.start();
    }
}
