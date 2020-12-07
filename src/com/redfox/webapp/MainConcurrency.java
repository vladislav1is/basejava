package com.redfox.webapp;

import com.redfox.webapp.util.LazySingleton;

import java.util.ArrayList;
import java.util.List;

public class MainConcurrency {
    public static final int THREADS_NUMBER = 10_000;
    private static int counter;
    private static final Object LOCK = new Object();

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName());

        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + ", " + getState());
                throw new IllegalStateException();
            }
        };
        thread0.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState());
            }

            private void inc() {
                synchronized (this) {
                    counter++;
                }
            }
        }
        ).start();

        System.out.println(thread0.getState());

        // All threads will stay in queue to obj mainConcurrency
        final MainConcurrency mainConcurrency = new MainConcurrency();
        List<Thread> threads = new ArrayList<>(THREADS_NUMBER);
        for (int i = 0; i < THREADS_NUMBER; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                }
            });
            thread.start();
            threads.add(thread);
        }
        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
//        Thread.sleep(500); // bad practice
        System.out.println(counter);
        LazySingleton.getInstance();
    }

    private synchronized void inc() {
        counter++;
//        // use current object as monitor
//        synchronized (this) {
//            wait(); // use if you want pause thread work and unblock method for another thread
//            notify(); // use if you want wake up waiting thread
//            counter++;
//        }
//        // use current class as monitor, use with static methods
//        synchronized (MainConcurrency.class) {
//            counter++;
//        }
//        // use obj LOCK as monitor
//        synchronized (LOCK) {
//            counter++;
//        }
    }
}
