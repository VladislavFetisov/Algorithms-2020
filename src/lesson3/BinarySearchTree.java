package lesson3;

import java.util.*;

import kotlin.NotImplementedError;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// attention: Comparable is supported but Comparator is not
public class BinarySearchTree<T extends Comparable<T>> extends AbstractSet<T> implements CheckableSortedSet<T> {

    private static class Node<T> {
        final T value;
        Node<T> left = null;
        Node<T> right = null;
        Node<T> parent = null;

        Node(T value) {
            this.value = value;
        }

    }


    private Node<T> root = null;

    private int size = 0;

    @Override
    public int size() {
        return size;
    }

    public Node<T> find(T value) {
        if (root == null) return null;
        return find(root, value);
    }

    private Node<T> find(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        if (comparison == 0) {
            return start;
        } else if (comparison < 0) {
            if (start.left == null) return start;
            return find(start.left, value);
        } else {
            if (start.right == null) return start;
            return find(start.right, value);
        }
    }

    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> closest = find(t);
        return closest != null && t.compareTo(closest.value) == 0;
    }

    /**
     * Добавление элемента в дерево
     * <p>
     * Если элемента нет в множестве, функция добавляет его в дерево и возвращает true.
     * В ином случае функция оставляет множество нетронутым и возвращает false.
     * <p>
     * Спецификация: {@link Set#add(Object)} (Ctrl+Click по add)
     * <p>
     * Пример
     */
    @Override
    public boolean add(T t) {
        Node<T> closest = find(t);
        int comparison = closest == null ? -1 : t.compareTo(closest.value);
        if (comparison == 0) {
            return false;
        }
        Node<T> newNode = new Node<>(t);
        if (closest == null) {
            root = newNode;
        } else if (comparison < 0) {
            assert closest.left == null;
            closest.left = newNode;
            newNode.parent = closest;
        } else {
            assert closest.right == null;
            closest.right = newNode;
            newNode.parent = closest;
        }
        size++;
        return true;

    }

    /**
     * Удаление элемента из дерева
     * <p>
     * Если элемент есть в множестве, функция удаляет его из дерева и возвращает true.
     * В ином случае функция оставляет множество нетронутым и возвращает false.
     * Высота дерева не должна увеличиться в результате удаления.
     * <p>
     * Спецификация: {@link Set#remove(Object)} (Ctrl+Click по remove)
     * <p>
     * Средняя
     */
    @Override
    public boolean remove(Object o) {
        Node<T> closest;
        if (o != null && !o.getClass().equals(Node.class)) {
            @SuppressWarnings("unchecked")
            T element = (T) o;
            closest = find(element);
            if (element.compareTo(closest.value) != 0) return false;
        } else closest = (Node<T>) o;
        if (closest.left == null) transplanting(closest, closest.right);
        else if (closest.right == null) transplanting(closest, closest.left);
        else {
            Node<T> next;
            next = closest.right;
            while (next.left != null) {
                next = next.left;
            }
            if (next.parent.value.compareTo(closest.value) != 0) {
                transplanting(next, next.right);
                next.right = closest.right;
                closest.right.parent = next;
            }
            transplanting(closest, next);
            next.left = closest.left;
            closest.left.parent = next;
        }
        size--;
        return true;
    }

    private void transplanting(Node<T> closest, Node<T> next) {
        if (closest.parent == null) root = next;
        else if (closest.parent.left != null && closest.parent.left.value.compareTo(closest.value) == 0)
            closest.parent.left = next;
        else closest.parent.right = next;
        if (next != null) next.parent = closest.parent;
    }

    private Node<T> treeMinimum(Node<T> start) {
        Node<T> current = start;
        if (current == null) throw new IllegalArgumentException("Вызвано из null");
        while (current.left != null) current = current.left;
        return current;
    }

    private Node<T> treeMaximum(Node<T> start) {
        Node<T> current = start;
        if (current == null) throw new IllegalArgumentException("Вызвано из null");
        while (current.right != null) current = current.right;
        return current;
    }

    private Node<T> findLocalRoot(Node<T> start) {
        Node<T> current = start;
        if (start.parent == null) throw new IllegalArgumentException("Родителя не существует");
        else
            while (current.value.compareTo(current.parent.value) > 0) current = current.parent;
        return current;
    }

    @Nullable
    @Override
    public Comparator<? super T> comparator() {
        return null;
    }


    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new BinarySearchTreeIterator(root);
    }

    public class BinarySearchTreeIterator implements Iterator<T> {
        private Node<T> current;
        private Node<T> max;
        private boolean trigger = false;
        private int count;

        private BinarySearchTreeIterator(Node<T> root) {
            if (root != null) {
                current = treeMinimum(root);
                max = treeMaximum(root);
            }
        }

        /**
         * Проверка наличия следующего элемента
         * <p>
         * Функция возвращает true, если итерация по множеству ещё не окончена (то есть, если вызов next() вернёт
         * следующий элемент множества, а не бросит исключение); иначе возвращает false.
         * <p>
         * Спецификация: {@link Iterator#hasNext()} (Ctrl+Click по hasNext)
         * <p>
         * Средняя
         */
        @Override
        public boolean hasNext() {
            return root != null && current.value.compareTo(max.value) != 0;
        }

        /**
         * Получение следующего элемента
         * <p>
         * Функция возвращает следующий элемент множества.
         * Так как BinarySearchTree реализует интерфейс SortedSet, последовательные
         * вызовы next() должны возвращать элементы в порядке возрастания.
         * <p>
         * Бросает NoSuchElementException, если все элементы уже были возвращены.
         * <p>
         * Спецификация: {@link Iterator#next()} (Ctrl+Click по next)
         * <p>
         * Средняя
         *
         * @return
         */
        @Override
        public T next() {
            count = 1;
            if (hasNext()) {
                Node<T> next;
                if (!trigger) {
                    next = current;
                    trigger = true;
                } else if (current.parent == null) next = treeMinimum(current.right);
                else {
                    if (current.parent.left != null
                            && current.parent.left.value.compareTo(current.value) == 0
                            && current.right == null) next = current.parent;
                    else if (current.right == null) next = findLocalRoot(current).parent;
                    else next = treeMinimum(current.right);
                }
                if (next != null) {
                    current = next;
                    return next.value;
                }
            }
            throw new IllegalStateException("Все элементы уже были возвращены");
        }


        /**
         * Удаление предыдущего элемента
         * <p>
         * Функция удаляет из множества элемент, возвращённый крайним вызовом функции next().
         * <p>
         * Бросает IllegalStateException, если функция была вызвана до первого вызова next() или же была вызвана
         * более одного раза после любого вызова next().
         * <p>
         * Спецификация: {@link Iterator#remove()} (Ctrl+Click по remove)
         * <p>
         * Сложная
         */
        @Override
        public void remove() {
            count--;
            if (!trigger || count < 0) throw new IllegalStateException("Неверный вызов remove");
            BinarySearchTree.this.remove(current);
        }
    }


    /**
     * Подмножество всех элементов в диапазоне [fromElement, toElement)
     * <p>
     * Функция возвращает множество, содержащее в себе все элементы дерева, которые
     * больше или равны fromElement и строго меньше toElement.
     * При равенстве fromElement и toElement возвращается пустое множество.
     * Изменения в дереве должны отображаться в полученном подмножестве, и наоборот.
     * <p>
     * При попытке добавить в подмножество элемент за пределами указанного диапазона
     * должен быть брошен IllegalArgumentException.
     * <p>
     * Спецификация: {@link SortedSet#subSet(Object, Object)} (Ctrl+Click по subSet)
     * (настоятельно рекомендуется прочитать и понять спецификацию перед выполнением задачи)
     * <p>
     * Очень сложная (в том случае, если спецификация реализуется в полном объёме)
     */
    @NotNull
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        // TODO
        throw new NotImplementedError();
    }

    /**
     * Подмножество всех элементов строго меньше заданного
     * <p>
     * Функция возвращает множество, содержащее в себе все элементы дерева строго меньше toElement.
     * Изменения в дереве должны отображаться в полученном подмножестве, и наоборот.
     * <p>
     * При попытке добавить в подмножество элемент за пределами указанного диапазона
     * должен быть брошен IllegalArgumentException.
     * <p>
     * Спецификация: {@link SortedSet#headSet(Object)} (Ctrl+Click по headSet)
     * (настоятельно рекомендуется прочитать и понять спецификацию перед выполнением задачи)
     * <p>
     * Сложная
     */
    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) {
        // TODO
        throw new NotImplementedError();
    }

    /**
     * Подмножество всех элементов нестрого больше заданного
     * <p>
     * Функция возвращает множество, содержащее в себе все элементы дерева нестрого больше toElement.
     * Изменения в дереве должны отображаться в полученном подмножестве, и наоборот.
     * <p>
     * При попытке добавить в подмножество элемент за пределами указанного диапазона
     * должен быть брошен IllegalArgumentException.
     * <p>
     * Спецификация: {@link SortedSet#tailSet(Object)} (Ctrl+Click по tailSet)
     * (настоятельно рекомендуется прочитать и понять спецификацию перед выполнением задачи)
     * <p>
     * Сложная
     */
    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        // TODO
        throw new NotImplementedError();
    }

    @Override
    public T first() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.value;
    }

    @Override
    public T last() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.value;
    }

    public int height() {
        return height(root);
    }

    private int height(Node<T> node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    public boolean checkInvariant() {
        return root == null || checkInvariant(root);
    }

    private boolean checkInvariant(Node<T> node) {
        Node<T> left = node.left;
        if (left != null && (left.value.compareTo(node.value) >= 0 || !checkInvariant(left))) return false;
        Node<T> right = node.right;
        return right == null || right.value.compareTo(node.value) > 0 && checkInvariant(right);
    }

}