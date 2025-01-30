package org.fslabs.Repository;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AccountRepository {
    private static final Map<Integer, Integer> MAP = IntStream.rangeClosed(1, 10)
            .boxed()
            .collect(Collectors.toConcurrentMap(
                    Function.identity(),
                    v -> 100
            ));


    public static Integer getBalance(int accountNumber) {
        return MAP.getOrDefault(accountNumber, -1);
    }

    public static Map<Integer, Integer> getAllAccounts() {
        return Collections.unmodifiableMap(MAP);
    }

    public static void deductAmount(int amount, int accountNumber) {
        MAP.computeIfPresent(accountNumber, (key, value) -> value - amount);
    }

    public static void addAmount(int amount, int accountNumber) {
        MAP.computeIfPresent(accountNumber, (key, value) -> value + amount);
    }
}
