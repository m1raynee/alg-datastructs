package com.m1raynee.coursework;

import java.util.Random;

public class PerformanceAnalyzer {

    private static final int NUM_KEYS = 50000;
    private static final int MAX_KEY_VALUE = 200000;

    public static void analyze() {
        System.out.println("--- Сравнительный анализ стратегий пробирования (Open Addressing) ---");
        System.out.printf("Количество операций (пар ключ-значение): %d\n", NUM_KEYS);
        System.out.printf("Максимальное значение ключа: %d\n\n", MAX_KEY_VALUE);

        Random rand = new Random();
        Integer[] keys = new Integer[NUM_KEYS];
        String[] values = new String[NUM_KEYS];
        for (int i = 0; i < NUM_KEYS; i++) {
            keys[i] = rand.nextInt(MAX_KEY_VALUE) + 1;
            values[i] = "Value_" + keys[i];
        }

        System.out.println("------------------------------------------------------------------");
        testStrategy("Линейное пробирование (Емкость = 2^k, LF=0.75)",
                i -> i, false, keys,
                values);
        System.out.println("------------------------------------------------------------------");
        testStrategy("Квадратичное пробирование (Емкость = Простое число, LF=0.75)",
                i -> i * i, true, keys,
                values);
        System.out.println("------------------------------------------------------------------");
    }

    private static void testStrategy(String name, ProbeStrategy strategy, boolean usePrimes, Integer[] keys,
            String[] values) {
        CustomHashMap<String> map = new CustomHashMap<>(strategy, 16, usePrimes);

        System.out.println("Стратегия: " + name);
        System.out.printf("Начальная емкость (M): %d\n", map.getCapacity());

        // 1. Операция PUT
        map.resetProbes();
        var startTime = System.nanoTime();
        for (int i = 0; i < NUM_KEYS; i++) {
            map.put(keys[i], values[i]);
        }
        var endTime = System.nanoTime();
        var putTime = endTime - startTime;
        var putProbes = map.getTotalProbes();

        System.out.printf("1. Вставка %d элементов (Конечная емкость: %d, loadFactor: %.2f)\n",
                map.getSize(), map.getCapacity(), (double) map.getSize() / map.getCapacity());
        System.out.printf("   - Время выполнения: %.3f мс\n", putTime / 1_000_000.0);
        System.out.printf("   - Общее количество проб (коллизий): %d\n", putProbes);
        System.out.printf("   - Среднее количество проб на операцию: %.2f\n", (double) putProbes / NUM_KEYS);

        // 2. Операция GET
        map.resetProbes();
        startTime = System.nanoTime();
        for (int i = 0; i < NUM_KEYS; i++) {
            if (map.get(keys[i]) == null) {
                System.err.println("Ошибка: Ключ не найден при поиске.");
            }
        }
        endTime = System.nanoTime();
        var getTime = endTime - startTime;
        var getProbes = map.getTotalProbes();

        System.out.printf("2. Поиск %d элементов\n", NUM_KEYS);
        System.out.printf("   - Время выполнения: %.3f мс\n", getTime / 1_000_000.0);
        System.out.printf("   - Общее количество проб (коллизий): %d\n", getProbes);
        System.out.printf("   - Среднее количество проб на операцию: %.2f\n", (double) getProbes / NUM_KEYS);

        // 3. Операция REMOVE
        map.resetProbes();
        startTime = System.nanoTime();
        for (int i = 0; i < NUM_KEYS; i += 2) {
            map.remove(keys[i]);
        }
        endTime = System.nanoTime();
        var removeTime = endTime - startTime;
        var removeProbes = map.getTotalProbes();

        System.out.printf("3. Удаление %d элементов (удалено: %d, осталось: %d)\n", NUM_KEYS / 2, NUM_KEYS / 2,
                map.getSize());
        System.out.printf("   - Время выполнения: %.3f мс\n", removeTime / 1_000_000.0);
        System.out.printf("   - Общее количество проб (коллизий): %d\n", removeProbes);
        System.out.printf("   - Среднее количество проб на операцию: %.2f\n",
                (double) removeProbes / (NUM_KEYS / 2));

        // 4. Проверка корректности
        map.resetProbes();
        int foundAfterRemoval = 0;
        for (int i = 1; i < NUM_KEYS; i += 2) {
            if (map.get(keys[i]) != null) {
                foundAfterRemoval++;
            }
        }
        System.out.printf("4. Проверка после удаления: Найдено %d из %d ожидаемых элементов.\n",
                foundAfterRemoval, NUM_KEYS - (NUM_KEYS / 2));
        System.out.printf("   - Среднее количество проб для поиска (оставшихся): %.2f\n",
                (double) map.getTotalProbes() / (NUM_KEYS / 2));
    }
}