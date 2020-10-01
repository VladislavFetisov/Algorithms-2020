package lesson1;

import kotlin.NotImplementedError;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

@SuppressWarnings("unused")
public class JavaTasks {
    /**
     * Сортировка времён
     * <p>
     * Простая
     * (Модифицированная задача с сайта acmp.ru)
     * <p>
     * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС AM/PM,
     * каждый на отдельной строке. См. статью википедии "12-часовой формат времени".
     * <p>
     * Пример:
     * <p>
     * 01:15:19 PM
     * 07:26:57 AM
     * 10:00:03 AM
     * 07:56:14 PM
     * 01:15:19 PM
     * 12:40:31 AM
     * <p>
     * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
     * сохраняя формат ЧЧ:ММ:СС AM/PM. Одинаковые моменты времени выводить друг за другом. Пример:
     * <p>
     * 12:40:31 AM
     * 07:26:57 AM
     * 10:00:03 AM
     * 01:15:19 PM
     * 01:15:19 PM
     * 07:56:14 PM
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortTimes(String inputName, String outputName) {
        throw new NotImplementedError();
    }
        /*int addition = 12;
        ArrayList<Integer> intTimes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputName), UTF_8))) {
            String line = reader.readLine();
            while (line != null) {

                if (!Pattern.matches("((0[0-9])|(1[0-2])):([0-5]\\d):([0-5]\\d) (AM|PM)", line))
                    throw new IllegalArgumentException();

                String[] currentTime = line.split("[: ]");
                if (currentTime[3].equals("AM")) {
                    addition = 0;
                    if (Integer.parseInt(currentTime[0]) == 12) addition = -12;
                }
                intTimes.add((Integer.parseInt(currentTime[0]) + addition) * 3600 +
                        Integer.parseInt(currentTime[1]) * 60 + Integer.parseInt(currentTime[2]));
                addition = 12;
                line = reader.readLine();
            }
        }
        int[] finalTime = new int[intTimes.size()];
        for (int i = 0; i < intTimes.size(); i++) {
            finalTime[i] = intTimes.get(i);
        }
        Sorts.mergeSort(finalTime);
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(outputName), UTF_8))) {
            String add1 = "";
            String add2 = "";
            String add3 = "";
            String add4 = "PM";
            for (int intTime : finalTime) {
                int hours = intTime / 3600;
                int minutes = (intTime - hours * 3600) / 60;
                int seconds = (intTime - hours * 3600) - minutes * 60;
                if (hours < 12) {
                    add4 = "AM";
                    if (hours == 0) hours = 12;
                    if (minutes < 10) add2 = "0";
                    if (seconds < 10) add3 = "0";
                } else hours -= 12;
                if (hours < 10) add1 = "0";

                writer.write(add1 + hours + ":"
                        + add2 + minutes + ":" + add3 + seconds + " " + add4 + System.lineSeparator());
                add1 = "";
                add2 = "";
                add3 = "";
                add4 = "PM";
            }
        }*/


    /**
     * Сортировка адресов
     * <p>
     * Средняя
     * <p>
     * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
     * где они прописаны. Пример:
     * <p>
     * Петров Иван - Железнодорожная 3
     * Сидоров Петр - Садовая 5
     * Иванов Алексей - Железнодорожная 7
     * Сидорова Мария - Садовая 5
     * Иванов Михаил - Железнодорожная 7
     * <p>
     * Людей в городе может быть до миллиона.
     * <p>
     * Вывести записи в выходной файл outputName,
     * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
     * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
     * <p>
     * Железнодорожная 3 - Петров Иван
     * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
     * Садовая 5 - Сидоров Петр, Сидорова Мария
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortAddresses(String inputName, String outputName) {
        throw new NotImplementedError();
    }
    /*try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputName), UTF_8))) {
            String line = reader.readLine();
            while (line != null) {
                if (!Pattern.matches("[А-Я]([а-я]+) [А-Я]([а-я]+) - [А-Я][а-я]+ [1-9]+", line))
                    throw new IllegalArgumentException();


                line = reader.readLine();
            }
        }*/

    /**
     * Сортировка температур
     * <p>
     * Средняя
     * (Модифицированная задача с сайта acmp.ru)
     * <p>
     * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
     * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
     * Например:
     * <p>
     * 24.7
     * -12.6
     * 121.3
     * -98.4
     * 99.5
     * -12.6
     * 11.0
     * <p>
     * Количество строк в файле может достигать ста миллионов.
     * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
     * Повторяющиеся строки сохранить. Например:
     * <p>
     * -98.4
     * -12.6
     * -12.6
     * 11.0
     * 24.7
     * 99.5
     * 121.3
     */
    static public void sortTemperatures(String inputName, String outputName) throws IOException {
        int min = -2730;
        int max = 5000;
        LinkedHashMap<Integer, Integer> map = new LinkedHashMap<>();

        for (int i = min; i < max + 1; i++) map.put(i, 0);//Ресурсоемкость,трудоемкость:O(max-min+1)

        try (BufferedReader reader = new BufferedReader
                (new InputStreamReader(new FileInputStream(inputName), UTF_8))) {
            String line = reader.readLine();
            while (line != null) {//Трудоемкость O(n)
                int number = (int) (Double.parseDouble(line) * 10);
                int value = map.get(number);
                map.put(number, value + 1);
                line = reader.readLine();
            }
        }

        try (BufferedWriter writer = new BufferedWriter
                (new OutputStreamWriter(new FileOutputStream(outputName), UTF_8))) {
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {//Трудоемкость:O(max-min+1)
                int repetition = entry.getValue();
                if (repetition != 0) {
                    for (int i = 0; i < repetition; i++)//Трудоемкость в худшем случае=O(n),в лучшем=O(1)
                        writer.write(((double) entry.getKey()) / 10 + System.lineSeparator());
                }
            }
        }
        //Данный алгоритм достигает худших результатов,при большом диапазоне температур и входных данных в виде
        // 1 температуры,которая повторяется n раз.Лучший случай достигается,когда n>>max-min+1 и у каждой температуры
        // только 1 повторение

    }


    /**
     * Сортировка последовательности
     * <p>
     * Средняя
     * (Задача взята с сайта acmp.ru)
     * <p>
     * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
     * <p>
     * 1
     * 2
     * 3
     * 2
     * 3
     * 1
     * 2
     * <p>
     * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
     * а если таких чисел несколько, то найти минимальное из них,
     * и после этого переместить все такие числа в конец заданной последовательности.
     * Порядок расположения остальных чисел должен остаться без изменения.
     * <p>
     * 1
     * 3
     * 3
     * 1
     * 2
     * 2
     * 2
     */
    static public void sortSequence(String inputName, String outputName) throws IOException {
        int max = 0, number = 0;
        ArrayList<Integer> elements = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader
                (new InputStreamReader(new FileInputStream(inputName), UTF_8))) {
            String line = reader.readLine();
            while (line != null) {//Трудоемкость O(n)
                int element = Integer.parseInt(line);
                if (element > max) max = element;
                elements.add(element);
                line = reader.readLine();
            }
        }
        int[] count = new int[max + 1];//Ресурсоемкость O(max+1)
        for (int element : elements) count[element]++;//Трудоемкость O(n)

        max = 0;
        for (int i = 0; i < count.length; i++) {//Трудоемкость O(max+1)
            int repetitionOfNumber = count[i];
            if (repetitionOfNumber > max) {
                max = repetitionOfNumber;
                number = i;
            }
        }
        try (BufferedWriter writer = new BufferedWriter
                (new OutputStreamWriter(new FileOutputStream(outputName), UTF_8))) {
            for (Integer element : elements) if (element != number) writer.write(element + System.lineSeparator());
            //Трудоемкость O(n-c)
            for (int i = 0; i < max; i++) writer.write(number + System.lineSeparator());
            //Трудоемкость O(c),где c-повторение максимального числа
        }
        //Итого:худший случай:O(max+1)(где max-максимальное число)+O(n)+O(n-c)+O(с)(где с-кол-во повторений max)=O(max+1)
        //Лучший случай:max<<n,поэтому трудоемкость=O(1)+O(n)+O(n-c)+O(c)=O(n)
    }
        /*HashMap<Integer, Integer> map = new HashMap<>();
        ArrayList<Integer> arrayList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputName), UTF_8))) {
            String line = reader.readLine();
            while (line != null) {
                int number = Integer.parseInt(line);
                arrayList.add(number);
                if (map.containsKey(number)) {
                    int value = map.get(number);
                    map.put(number, value + 1);
                } else map.put(number, 1);
                line = reader.readLine();
            }
        }
        Map.Entry<Integer, Integer> maxEntry = null;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
                maxEntry = entry;//сделать красивее
            else if (entry.getValue().compareTo(maxEntry.getValue()) == 0) {
                if (maxEntry.getKey() > entry.getKey()) maxEntry = entry;
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputName), UTF_8))) {
            if (maxEntry != null) {
                for (Integer number : arrayList)
                    if (!number.equals(maxEntry.getKey())) writer.write(number + System.lineSeparator());
                for (int i = 0; i < maxEntry.getValue(); i++)
                    writer.write(maxEntry.getKey() + System.lineSeparator());
            }
        }*/

    /**
     * Соединить два отсортированных массива в один
     * <p>
     * Простая
     * <p>
     * Задан отсортированный массив first и второй массив second,
     * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
     * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
     * <p>
     * first = [4 9 15 20 28]
     * second = [null null null null null 1 3 9 13 18 23]
     * <p>
     * Результат: second = [1 3 4 9 9 13 15 20 23 28]
     */
    static <T extends Comparable<T>> void mergeArrays(T[] first, T[] second) {
        throw new NotImplementedError();
    }
}
