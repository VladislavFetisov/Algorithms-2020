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

        public Node getParent() {
            return parent;
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

    private Character previousChr = 0;

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
        StringBuilder word = new StringBuilder();
        Integer i = 0;

        return new Iterator<>() {
            boolean hasNext;

            @Override
            public boolean hasNext() {
                hasNext = goToWordEnd(root, word, i);
                return hasNext;
            }

            @Override
            public String next() {
                if (hasNext) return word.toString();
                throw new NoSuchElementException("Нечего больше возвращать");
            }

            @Override
            public void remove() {

            }
        };
    }

//    private boolean findWord(StringBuilder word) {
//        Node lastNode = findNode(word.toString());
//        assert lastNode != null;
//        if (goToWordEnd(lastNode, word, )) return true;
//        char chr = word.charAt(word.length() - 1);
//        goToWordEnd(lastNode.parent, word.deleteCharAt(word.length() - 1));
//    }

    /**
     * Must be started from the root
     */
    private boolean goToWordEnd(Node current, StringBuilder word, Integer i) {
        if (i < word.length()) {
            int var = i;
            Node needed=null;
            for (Map.Entry<Character, Node> node : current.getChildren().entrySet()) {
                if (node.getKey().equals(word.charAt(i))) {

                    var++;
                    if (goToWordEnd(node.getValue(), word, var)) return true;
                    else {
                        char previous = word.charAt((word.length() - 1));
                        word.deleteCharAt(word.length() - 1);
                        if (findNewWord(current, word, previous)) return true;
                    }
                }
            }
        } else if (word.length() == 0 && findNewWord(current, word, null)) return true;
        else if (findNewWord(current, word, (char) 0)) return true;
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


    private Optional<Map.Entry<Character, Node>> getNext(Map<Character, Node> children, Character previous) {
        for (Iterator<Map.Entry<Character, Node>> it = children.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<Character, Node> entry = it.next();
            if (entry.getKey().equals(previous) && it.hasNext()) return Optional.of(it.next());
        }
        return Optional.empty();
    }
}
//for (Iterator<Map.Entry<Character, Node>> it = root.getChildren().entrySet().iterator(); it.hasNext(); ) {
//            if (previousChr != 0) {
//                Map.Entry<Character, Node> entry = it.next();
//                if (entry.getKey().equals(previousChr) && it.hasNext()) {
//                    word.append(entry.getKey());
////                    if (goToWordEnd(it.next().getValue(), word, -1, true)) return true;
//                    word.deleteCharAt(word.length() - 1);
//                    return it.next().getKey();
//                }
//            }
//        }
//        return previousChr;


//if (i < word.length()) {
//            if (!isWordEnd) {
//                for (Map.Entry<Character, Node> node : current.getChildren().entrySet())
//                    if (node.getKey().equals(word.charAt(i))) goToWordEnd(node.getValue(), word, i++, false);
//            }
//
//        } else {
//            for (Map.Entry<Character, Node> node : current.getChildren().entrySet()) {
//                if (node.getKey().equals((char) 0)) return true;
//                word.append(node.getKey());
//                if (goToWordEnd(node.getValue(), word, -1, true))
//                    word.deleteCharAt(word.length() - 1);
//            }
//
//        }

//else if (i != 0) {
//            boolean canWork = false;
//            for (Map.Entry<Character, Node> node : current.getChildren().entrySet()) {
//                if (node.getKey().equals((char) 0)) canWork = true;
//                else if (canWork) {
//                    word.append(node.getKey());
//                    if (goToWordEnd(node.getValue(), word, 0, true)) return true;
//                    word.deleteCharAt(word.length() - 1);
//                }
//            }
//        }

//if (i < word.length() && !isWordEnd) {
//            int var = i;
//            for (Map.Entry<Character, Node> node : current.getChildren().entrySet()) {
//                if (node.getKey().equals(word.charAt(i))) {
//                    var++;
//                    if (goToWordEnd(node.getValue(), word, var, false, (char) 0)) return true;
//                    else if (goToWordEnd(node.getValue(), word, 0, true)) return true;
//                }
//            }
//        } else {
//            boolean canWork = false;
//            for (Map.Entry<Character, Node> node : current.getChildren().entrySet()) {
//                if (node.getKey().equals(previousChr)) canWork = true;
//                else if (canWork) {
//                    if (node.getKey().equals((char) 0)) return true;
//                    word.append(node.getKey());
//                    if (goToWordEnd(node.getValue(), word, 0, true)) return true;
//                    word.deleteCharAt(word.length() - 1);
//                }
//
//            }
//        }