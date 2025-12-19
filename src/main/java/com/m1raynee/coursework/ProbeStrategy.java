package com.m1raynee.coursework;

/**
 * Интерфейс для стратегии пробирования.
 */
@FunctionalInterface
public interface ProbeStrategy {
    /**
     * Вычисляет смещение (добавочный индекс) для i-й попытки пробирования.
     * 
     * @param i Номер попытки пробирования (начиная с 0 для первой попытки)
     * @return Смещение индекса (>= 0)
     */
    int probe(int i);
}