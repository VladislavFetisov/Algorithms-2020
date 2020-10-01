package lesson7;

import kotlin.NotImplementedError;

import java.util.List;

@SuppressWarnings("unused")
public class JavaDynamicTasks {
    /**
     * Наибольшая общая подпоследовательность.
     * Средняя
     * <p>
     * Дано две строки, например "nematode knowledge" и "empty bottle".
     * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
     * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
     * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
     * Если общей подпоследовательности нет, вернуть пустую строку.
     * Если есть несколько самых длинных общих подпоследовательностей, вернуть любую из них.
     * При сравнении подстрок, регистр символов *имеет* значение.
     *
     * @return
     */
    public static String longestCommonSubSequence(String first, String second) {
        int length1 = first.length();
        int length2 = second.length();
        Entry[][] table = new Entry[length1][length2];
        boolean triggerJ = false;
        for (int i = 0; i < length1; i++) {
            char chrI = first.charAt(i);
            for (int j = 0; j < length2; j++) {
                char chrJ = second.charAt(j);
                if (triggerJ) {
                    if (i == 0) table[i][j] = table[i][j - 1];
                    else if (table[i][j - 1].length >= table[i - 1][j].length) table[i][j] = table[i][j - 1];
                    else table[i][j] = table[i - 1][j];
                } else if (chrJ == chrI) {
                    if (i == 0) table[i][j] = new Entry(String.valueOf(chrI), 1);
                    else if (j != 0) {
                        Entry entry = table[i][j - 1];
                        table[i][j] = new Entry(entry.record + chrI, entry.length + 1);
                    } else table[i][j] = new Entry(String.valueOf(chrI), 1);
                    triggerJ = true;
                } else {
                    if (i == 0) table[i][j] = new Entry("", 0);
                    else table[i][j] = table[i - 1][j];
                }
            }
            triggerJ = false;
        }
        return table[length1 - 1][length2 - 1].record;
    }

    private static class Entry {
        String record;
        Integer length;

        Entry(String record, Integer length) {
            this.record = record;
            this.length = length;
        }

        @Override
        public String toString() {
            return "(" + record + "," + length + ")";
        }

    }

    /**
     * Наибольшая возрастающая подпоследовательность
     * Сложная
     * <p>
     * Дан список целых чисел, например, [2 8 5 9 12 6].
     * Найти в нём самую длинную возрастающую подпоследовательность.
     * Элементы подпоследовательности не обязаны идти подряд,
     * но должны быть расположены в исходном списке в том же порядке.
     * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
     * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
     * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
     */
    public static List<Integer> longestIncreasingSubSequence(List<Integer> list) {
        throw new NotImplementedError();
    }

    /**
     * Самый короткий маршрут на прямоугольном поле.
     * Средняя
     * <p>
     * В файле с именем inputName задано прямоугольное поле:
     * <p>
     * 0 2 3 2 4 1
     * 1 5 3 4 6 2
     * 2 6 2 5 1 3
     * 1 4 3 2 6 2
     * 4 2 3 1 5 0
     * <p>
     * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
     * В каждой клетке записано некоторое натуральное число или нуль.
     * Необходимо попасть из верхней левой клетки в правую нижнюю.
     * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
     * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
     * <p>
     * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
     */
    public static int shortestPathOnField(String inputName) {
        throw new NotImplementedError();
    }

    // Задачу "Максимальное независимое множество вершин в графе без циклов"
    // смотрите в уроке 5
}
