package com.urise.webapp;

public class MainString {
    public static void main(String[] args) {
        String[] strArray = new String[]{"1", "2", "3", "4", "5"};
//        String result = "";
        StringBuilder sb = new StringBuilder();
        for (String str : strArray) {
//            result += str + ", ";
            sb.append(str).append(", ");
        }
        System.out.println(sb);
        String s1 = new String("abc");
        String s2 = new String("abc");
        System.out.println(s1 == s2);
    }
}
