package com.m1raynee.coursework;

public class Main {
    public static void main(String[] args) {

        // 1. Демонстрация работы основных операций
        System.out.println("--- Демонстрация работы (Оптимизированное Квадратичное пробирование с Prime Capacity) ---");
        // Ближайшее простое число >= 4 это 5.
        CustomHashMap<String> demoMap = new CustomHashMap<>(i -> i * i, -1, true);

        System.out.printf("Начальная емкость (Простое число): %d\n", demoMap.getCapacity());

        // Вставка: (1, A), (6, B), (11, C) -> Все хэшируются в 1 % 5
        System.out.println("Вставка: (1, A), (6, B), (11, C)");
        demoMap.put(1, "A");
        demoMap.put(6, "B");
        demoMap.put(11, "C");

        System.out.printf("Текущая емкость: %d, Размер: %d\n", demoMap.getCapacity(), demoMap.getSize());

        System.out.println("Поиск 6: " + demoMap.get(6));
        System.out.println("Поиск 10 (отсутствует): " + demoMap.get(10));

        System.out.println("\nУдаление 6...");
        demoMap.remove(6);
        System.out.println("Содержит ключ 6 после удаления: " + demoMap.containsKey(6));

        demoMap.put(6, "B");
        System.out.println("\nПопытка вставки (16, D) (trigger resize)...");
        demoMap.put(16, "D"); // Trigger resize
        System.out.printf("После вставки 16: Емкость: %d, Размер: %d\n", demoMap.getCapacity(), demoMap.getSize());

        System.out.println("\nИтерация по активным элементам:");
        for (MapEntry<String> entry : demoMap) {
            System.out.println("Ключ: " + entry.key + ", Значение: " + entry.value);
        }

        // 2. Сравнительный анализ
        System.out.println("\n" + "=".repeat(80));
        PerformanceAnalyzer.analyze();
        System.out.println("=".repeat(80));

        System.out.println(
                "\nВывод: Оптимальное квадратичное пробирование (с простой емкостью) позволяет безопасно использовать loadFactor 0.75 и уменьшает кластеризацию по сравнению с линейным пробированием.");
    }
}
