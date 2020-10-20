package com.redfox.webapp;

import com.redfox.webapp.model.SectionType;

public class TestSingleton {
    private static TestSingleton instance;

    private TestSingleton() {
    }

    public static TestSingleton getInstance() {
        if (instance == null) instance = new TestSingleton();
        return instance;
    }

    public static void main(String[] args) {
        TestSingleton.getInstance();
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
