package com.m1raynee.coursework;

public class CapacityUtils {
    /**
     * Находит ближайшую степень двойки, большую или равную n.
     */
    public static int nextPowerOfTwo(int n) {
        if (n <= 1)
            return 1;
        int power = 2;
        while (power < n) {
            power *= 2;
        }
        return power;
    }

    /**
     * Проверяет, является ли число простым.
     */
    public static boolean isPrime(int n) {
        if (n <= 1)
            return false;
        if (n <= 3)
            return true;
        if (n % 2 == 0 || n % 3 == 0)
            return false;
        for (int i = 5; i * i <= n; i = i + 6) {
            if (n % i == 0 || n % (i + 2) == 0)
                return false;
        }
        return true;
    }

    /**
     * Находит ближайшее простое число, большее или равное n.
     */
    public static int getNextPrime(int n) {
        int p = n;
        while (true) {
            if (isPrime(p)) {
                return p;
            }
            p++;
        }
    }

    /**
     * Выбирает оптимальную емкость в зависимости от стратегии пробирования.
     * Квадратичное пробирование оптимально работает с простым числом.
     */
    public static int getOptimalCapacity(int n, boolean usePrimes) {
        if (usePrimes) {
            // Для Quadratic Probing выбираем ближайшее простое число >= n
            return getNextPrime(n);
        } else {
            // Для Linear Probing сохраняем Power of Two (для оптимизации индексации)
            return nextPowerOfTwo(n);
        }
    }
}