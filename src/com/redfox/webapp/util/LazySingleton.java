package com.redfox.webapp.util;

public final class LazySingleton {
    private static volatile LazySingleton INSTANCE;

    private LazySingleton() {
    }

    //Initialization-on-demand holder idiom
    //https://en.wikipedia.org/wiki/Initialization-on-demand_holder_idiom
    //Почему данная реализация является ленивой
    //так как загрузка Java классов выполняется только по требованию!(По встрече в коде)
    private static class LazySingletonHolder {
        private static final LazySingleton INSTANCE = new LazySingleton();
    }

    public static LazySingleton getInstance() {
        return LazySingletonHolder.INSTANCE;
    }

    //Double checked locking
    //https://en.wikipedia.org/wiki/Double-checked_locking
//    public static LazySingleton getInstance() {
//        if (INSTANCE == null) {
//            synchronized (LazySingleton.class) {
//                if (INSTANCE == null) {
//                    INSTANCE = new LazySingleton();
//                }
//            }
//        }
//        return INSTANCE;
//    }
}
