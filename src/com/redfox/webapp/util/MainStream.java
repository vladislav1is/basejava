package com.redfox.webapp.util;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainStream {

    public static void main(String[] args) {
//        int[] values = new int[]{1, 2, 3, 3, 2, 3};
        int[] values = new int[]{8, 9};
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
        values = Arrays.stream(values).
                filter(value -> 0 < value && value < 10).distinct().sorted().toArray();

        return getSum(values);
    }

    private static int getSum(int[] values) {
        int counter = values.length - 1;
        int sum = 0;
        for (int i = 0; i < values.length; i++) {
            sum = (sum + (values[i] * (int) Math.pow(10, counter)));
            counter--;
        }
        return sum;
    }

    /*
        Метод принимает список цифр и если сумма всех чисел нечетная - удалить все нечетные, если четная - удалить все четные.
        Сложность алгоритма O(N).
     */
    static List<Integer> oddOrEven(List<Integer> integers) {
//        System.out.println("Sum = " + integers.stream()
//                .reduce((acc, x) -> acc + x)
//                .get());
        Stream<Integer> integersStream = integers.stream();
        return Stream.of(integersStream
                .reduce((acc, x) -> acc + x)
                .get())
                .flatMap((Function<Integer, Stream<Integer>>) x -> {
                    if (x % 2 == 0) {
                        return integersStream.filter(e -> e % 2 != 0);
                    } else {
                        return integersStream.filter(o -> o % 2 == 0);
                    }
                }).collect(Collectors.toList());
    }
}