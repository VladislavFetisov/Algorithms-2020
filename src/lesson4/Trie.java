package lesson4;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Префиксное дерево для строк
 */
public class Trie extends AbstractSet<String> implements Set<String> {

    public static class Node {
        Map<Character, Node> children = new LinkedHashMap<>();

        Node parent = null;

        public Map<Character, Node> getChildren() {
            return children;
        }


        @Override
        public String toString() {
            return "Node{" +
                    "children=" + children +
                    '}';
        }
    }

    private Node root = new Node();

    private int size = 0;


    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        root.children.clear();
        size = 0;
    }

    private String withZero(String initial) {
        return initial + (char) 0;
    }

    @Nullable
    public Node findNode(String element) {
        Node current = root;
        for (char character : element.toCharArray()) {
            if (current == null) return null;
            current = current.children.get(character);
        }
        return current;
    }

    @Override
    public boolean contains(Object o) {
        String element = (String) o;
        return findNode(withZero(element)) != null;
    }

    @Override
    public boolean add(String element) {
        Node current = root;
        boolean modified = false;
        for (char character : withZero(element).toCharArray()) {
            Node child = current.children.get(character);
            if (child != null) {
                current = child;
            } else {
                modified = true;
                Node newChild = new Node();
                newChild.parent = current;
                current.children.put(character, newChild);
                current = newChild;
            }
        }
        if (modified) {
            size++;
        }
        return modified;
    }

    @Override
    public boolean remove(Object o) {
        String element = (String) o;
        Node current = findNode(element);
        if (current == null) return false;
        if (current.children.remove((char) 0) != null) {
            size--;
            return true;
        }
        return false;
    }

    /**
     * Итератор для префиксного дерева
     * <p>
     * Спецификация: {@link Iterator} (Ctrl+Click по Iterator)
     * <p>
     */
    @NotNull
    @Override
    public Iterator<String> iterator() {
        return new Iterator<>() {
            final StringBuilder word = new StringBuilder();
            final Integer i = 0;
            int count = 0;

            @Override
            public boolean hasNext() {
                return count < size;
            }

            @Override
            public String next() {
                if (hasNext() && goToWordEnd(root, word, i)) {
                    count++;
                    return word.toString();
                }
                throw new IllegalStateException("Нечего больше возвращать");
            }

            @Override
            public void remove() {

            }
        };
    }

    /**
     * Must be started from the root
     */
    private boolean goToWordEnd(Node current, StringBuilder word, Integer i) {
        if (i < word.length()) {
            int var = i;
            Node needed = null;
            for (Map.Entry<Character, Node> node : current.getChildren().entrySet())
                if (node.getKey().equals(word.charAt(i))) {
                    needed = node.getValue();
                }
            if (needed != null) {
                var++;
                if (goToWordEnd(needed, word, var)) return true;
                else {
                    char previous = word.charAt((word.length() - 1));
                    word.deleteCharAt(word.length() - 1);
                    return findNewWord(current, word, previous);
                }
            }
        } else if (word.length() == 0 && findNewWord(current, word, null)) return true;
        else return findNewWord(current, word, (char) 0);
        return false;
    }

    private boolean findNewWord(Node current, StringBuilder word, Character previousChr) {
        boolean canWork = false;
        for (Map.Entry<Character, Node> node : current.getChildren().entrySet()) {
            if (previousChr == null) canWork = true;
            if (canWork) {
                if (node.getKey().equals((char) 0)) return true;
                word.append(node.getKey());
                if (findNewWord(node.getValue(), word, null)) return true;
                word.deleteCharAt(word.length() - 1);
            }
            if (node.getKey().equals(previousChr)) canWork = true;
        }
        return false;
    }
}