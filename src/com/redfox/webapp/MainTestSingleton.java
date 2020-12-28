package com.redfox.webapp;

import com.redfox.webapp.model.SectionType;

public class MainTestSingleton {
    private static MainTestSingleton instance;

    private MainTestSingleton() {
    }

    public static MainTestSingleton getInstance() {
        if (instance == null) instance = new MainTestSingleton();
        return instance;
    }

    public static void main(String[] args) {
        MainTestSingleton.getInstance();
        Singleton instance = Singleton.valueOf("INSTANCE");
        System.out.println(instance);
        System.out.println(instance.ordinal());
        for (SectionType type: SectionType.values()) {
            System.out.println(type.getTitle());
        }
    }

    public enum Singleton {
        INSTANCE
    }
}
