package com.redfox.webapp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainStream {

    public static void main(String[] args) {
//        int[] values = new int[]{1, 2, 3, 3, 2, 3};
        int[] values = new int[]{8, 9};
        System.out.println(minValue(values));
        System.out.println(oddOrEven(Arrays.asList(1, 2, 3, 4, 5)));
    }

    /*
        The method accepts an array of digits from 1 to 9, you need to select unique ones and return the minimum possible number,
        composed of these unique numbers. For example {1,2,3,3,2,3} will return 123 and {9,8} will return 89
    */
    static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (left, right) -> left * 10 + right);
    }

    /*
        The method accepts a list of digits and if the sum of all numbers is odd, remove all odd ones,
        if even, delete all even ones.
    */
    static List<Integer> oddOrEven(List<Integer> integers) {
        final int sumDivRemainder = integers.stream()
                .reduce(0, Integer::sum) % 2;
        return integers.stream()
                .filter(x -> sumDivRemainder != x % 2)
                .collect(Collectors.toList());
    }
}