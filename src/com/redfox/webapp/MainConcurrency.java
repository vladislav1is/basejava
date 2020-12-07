package com.redfox.webapp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MainConcurrency {
    private static final int THREADS_NUMBER = 10_000;
    private int counter;
    private final AtomicInteger atomicCounter = new AtomicInteger();

    //    private static final Object LOCK = new Object();
//    private static final Lock lock = new ReentrantLock();
    private static final ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    private static final Lock WRITE_LOCK = reentrantReadWriteLock.writeLock();
    private static final Lock READ_LOCK = reentrantReadWriteLock.readLock();
    private static final ThreadLocal<SimpleDateFormat> DATE_FORMAT = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        }
    };
//    private static final ThreadLocal<DateTimeFormatter> DATE_FORMAT = ThreadLocal.withInitial(() -> DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));

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
//                    counter++;
                }
            }
        }
        ).start();

        System.out.println(thread0.getState());

        // All threads will stay in queue to obj mainConcurrency
        final MainConcurrency mainConcurrency = new MainConcurrency();
        CountDownLatch latch = new CountDownLatch(THREADS_NUMBER);
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
//        CompletionService completionService = new ExecutorCompletionService(executorService);
//
//        List<Thread> threads = new ArrayList<>(THREADS_NUMBER);

        for (int i = 0; i < THREADS_NUMBER; i++) {
            Future<Integer> future = executorService.submit(() ->
//            Thread thread = new Thread(() ->
            {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                    System.out.println(DATE_FORMAT.get().format(new Date()));
//                    System.out.println(LocalDate.now().format(DATE_FORMAT));
                }
                latch.countDown();
                return 5;
            });
//            thread.start();
//            threads.add(thread);
        }

/*
        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
*/
        latch.await(10, TimeUnit.SECONDS);
        executorService.shutdown();
//        System.out.println(mainConcurrency.counter);
        System.out.println(mainConcurrency.atomicCounter);
    }

    // use synchronized in method name then current obj will be your monitor
    private void inc() {
        atomicCounter.incrementAndGet();
/*
        lock.lock();
        try {
            counter++;
        } finally {
            lock.unlock();
        }
*/

/*
        // use current object as monitor
        synchronized (this) {
            wait(); // use if you want pause thread work and unblock method for another thread
            notify(); // use if you want wake up waiting thread
            counter++;
        }
*/

/*
        // use current class as monitor, use with static methods
        synchronized (MainConcurrency.class) {
            counter++;
        }
 */

/*
        // use obj LOCK as monitor
        synchronized (LOCK) {
            counter++;
        }
 */
    }
}
