package com.redfox.webapp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainStream {

    public static void main(String[] args) {
        int[] values = new int[]{1, 2, 3, 3, 2, 3};
//        int[] values = new int[]{8, 9};
        System.out.println(minValue(values));
        //
        System.out.println("===================================");
        //
        System.out.println(oddOrEven(Arrays.asList(1, 2, 3, 4, 5)));
    }

    /*
        Метод принимает массив цифр от 1 до 9, надо выбрать уникальные и вернуть минимально возможное число,
        составленное из этих уникальных цифр. Например {1,2,3,3,2,3} вернет 123, а {9,8} вернет 89
     */
    static int minValue(int[] values) {
        return Arrays.stream(values).distinct().sorted()
                .reduce((left, right) -> left * 10 + right).getAsInt();
    }

    /*
        Метод принимает список цифр и если сумма всех чисел нечетная - удалить все нечетные, если четная - удалить все четные.
        Сложность алгоритма O(N).
     */
    static List<Integer> oddOrEven(List<Integer> integers) {
//        System.out.println("Sum = " + integers.stream()
//                .reduce((acc, x) -> acc + x)
//                .get());
        Integer sum = integers.stream()
                .reduce((acc, x) -> acc + x)
                .get();
        return integers.stream().filter(x -> (sum % 2 == 0) == (x % 2 != 0)).collect(Collectors.toList());
    }
}