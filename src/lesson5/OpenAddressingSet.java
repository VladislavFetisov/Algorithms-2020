package lesson5;

import org.jetbrains.annotations.NotNull;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;

public class OpenAddressingSet<T> extends AbstractSet<T> {

    private final int bits;

    private final int capacity;

    private final Object[] storage;

    private int size = 0;

    private final Object empty = new Object();

    private int startingIndex(Object element) {
        return element.hashCode() & (0x7FFFFFFF >> (31 - bits));
    }

    public OpenAddressingSet(int bits) {
        if (bits < 2 || bits > 31) {
            throw new IllegalArgumentException();
        }
        this.bits = bits;
        capacity = 1 << bits;
        storage = new Object[capacity];
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * Проверка, входит ли данный элемент в таблицу
     */
    @Override
    public boolean contains(Object o) {
        int index = startingIndex(o);
        Object current = storage[index];
        while (current != null) {//баг
            if (current.equals(o)) {
                return true;
            }
            index = (index + 1) % capacity;
            current = storage[index];
        }
        return false;
    }

    /**
     * Добавление элемента в таблицу.
     * <p>
     * Не делает ничего и возвращает false, если такой же элемент уже есть в таблице.
     * В противном случае вставляет элемент в таблицу и возвращает true.
     * <p>
     * Бросает исключение (IllegalStateException) в случае переполнения таблицы.
     * Обычно Set не предполагает ограничения на размер и подобных контрактов,
     * но в данном случае это было введено для упрощения кода.
     */
    @Override
    public boolean add(T t) {
        int startingIndex = startingIndex(t);
        int index = startingIndex;
        Object current = storage[index];
        while (current != null) {
            if (current.equals(t)) return false;

            if (current.equals(empty)) break;

            index = (index + 1) % capacity;
            if (index == startingIndex) {
                throw new IllegalStateException("Table is full");
            }
            current = storage[index];
        }
        storage[index] = t;
        size++;
        return true;
    }

    /**
     * Удаление элемента из таблицы
     * <p>
     * Если элемент есть в таблица, функция удаляет его из дерева и возвращает true.
     * В ином случае функция оставляет множество нетронутым и возвращает false.
     * Высота дерева не должна увеличиться в результате удаления.
     * <p>
     * Спецификация: {@link Set#remove(Object)} (Ctrl+Click по remove)
     * <p>
     * Средняя
     */
    @Override
    public boolean remove(Object o) {
        int startingIndex = startingIndex(o);
        int currentIndex = startingIndex;
        Object current = storage[currentIndex];
        while (current != null) {
            if (current.equals(empty)) break;

            if (current.getClass() != o.getClass()) throw new ClassCastException();

            if (current.equals(o)) {
                size--;
                storage[currentIndex] = empty;
                return true;
            }
            currentIndex = (currentIndex + 1) % capacity;
            current = storage[currentIndex];
            if (currentIndex == startingIndex) return false;
        }
        return false;
    }
    //Ресурсоемкость:на месте.Трудоемкость O(n)
    /**
     * Создание итератора для обхода таблицы
     * <p>
     * Не забываем, что итератор должен поддерживать функции next(), hasNext(),
     * и опционально функцию remove()
     * <p>
     * Спецификация: {@link Iterator} (Ctrl+Click по Iterator)
     * <p>
     * Средняя (сложная, если поддержан и remove тоже)
     *
     * @return
     */
    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private int cursor = 0;
            private int i = 0;
            boolean isHasNext = false;

            @Override
            public boolean hasNext() {
                return cursor < size;
            }

            @Override
            public T next() {
                if (hasNext()) {
                    while (storage[i] == null || storage[i] == empty) {
                        i++;
                    }
                    i++;
                    cursor++;
                    isHasNext = true;
                    return (T) storage[i - 1];
                }
                throw new IllegalStateException();
            }

            @Override
            public void remove() {
                if (!isHasNext)
                    throw new IllegalStateException();
                storage[i - 1] = empty;
                size--;
                cursor--;
            }
        };
    }//O(1)
}
