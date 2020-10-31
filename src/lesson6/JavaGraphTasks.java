package lesson6;

import kotlin.NotImplementedError;

import java.util.*;

import static lesson6.Graph.Edge;
import static lesson6.Graph.Vertex;
import static lesson6.JavaGraphTasks.VertexData.VertexColour.*;

@SuppressWarnings("unused")
public class JavaGraphTasks {

    /**
     * Эйлеров цикл.
     * Средняя
     * <p>
     * Дан граф (получатель). Найти по нему любой Эйлеров цикл.
     * Если в графе нет Эйлеровых циклов, вернуть пустой список.
     * Соседние дуги в списке-результате должны быть инцидентны друг другу,
     * а первая дуга в списке инцидентна последней.
     * Длина списка, если он не пуст, должна быть равна количеству дуг в графе.
     * Веса дуг никак не учитываются.
     * <p>
     * Пример:
     * <p>
     * G -- H
     * |    |
     * A -- B -- C -- D
     * |    |    |    |
     * E    F -- I    |
     * |              |
     * J ------------ K
     * <p>
     * Вариант ответа: A, E, J, K, D, C, H, G, B, C, I, F, B, A
     * <p>
     * Справка: Эйлеров цикл -- это цикл, проходящий через все рёбра
     * связного графа ровно по одному разу
     */
    public static List<Edge> findEulerLoop(Graph graph) {
        throw new NotImplementedError();
    }

    /**
     * Минимальное остовное дерево.
     * Средняя
     * <p>
     * Дан связный граф (получатель). Найти по нему минимальное остовное дерево.
     * Если есть несколько минимальных остовных деревьев с одинаковым числом дуг,
     * вернуть любое из них. Веса дуг не учитывать.
     * <p>
     * Пример:
     * <p>
     * G -- H
     * |    |
     * A -- B -- C -- D
     * |    |    |    |
     * E    F -- I    |
     * |              |
     * J ------------ K
     * <p>
     * Ответ:
     * <p>
     * G    H
     * |    |
     * A -- B -- C -- D
     * |    |    |
     * E    F    I
     * |
     * J ------------ K
     */
    public static Graph minimumSpanningTree(Graph graph) {
        throw new NotImplementedError();
    }

    /**
     * Максимальное независимое множество вершин в графе без циклов.
     * Сложная
     * <p>
     * Дан граф без циклов (получатель), например
     * <p>
     *      G -- H -- J
     *      |
     * A -- B -- D
     * |         |
     * C -- F    I
     * |
     * E
     * <p>
     * Найти в нём самое большое независимое множество вершин и вернуть его.
     * Никакая пара вершин в независимом множестве не должна быть связана ребром.
     * <p>
     * Если самых больших множеств несколько, приоритет имеет то из них,
     * в котором вершины расположены раньше во множестве this.vertices (начиная с первых).
     * <p>
     * В данном случае ответ (A, E, F, D, G, J)
     * <p>
     * Если на входе граф с циклами, бросить IllegalArgumentException
     * <p>
     * Эта задача может быть зачтена за пятый и шестой урок одновременно
     */


    public static class VertexData {
        public enum VertexColour {
            WHITE, GREY, BlACK
        }

        private VertexColour vertexColour;
        private Vertex previous;

        VertexData(VertexColour vertexColour, Vertex previous) {
            this.vertexColour = vertexColour;
            this.previous = previous;
        }
    }

    private static void classicDfs(Graph graph, Vertex vertex, Map<Vertex, VertexData> info, List<Integer> a) {
        info.get(vertex).vertexColour = GREY;
        for (Vertex neighbour : graph.getNeighbors(vertex)) {
            if (info.get(neighbour).vertexColour == WHITE) {
                info.get(neighbour).previous = vertex;
                classicDfs(graph, neighbour, info, a);
            }
            if (info.get(neighbour).vertexColour == GREY && !info.get(vertex).previous.equals(neighbour))
                a.add(1);
        }
        info.get(vertex).vertexColour = BlACK;
    }

    private static boolean isCyclical(Graph graph) {
        HashMap<Vertex, VertexData> map = new HashMap<>();
        Boolean trigger=false;
        ArrayList<Integer> a = new ArrayList<>();
        for (Vertex vertex : graph.getVertices()) map.put(vertex, new VertexData(WHITE, null));
        for (Vertex vertex : graph.getVertices()) {
            if (map.get(vertex).vertexColour == WHITE) classicDfs(graph, vertex, map, a);
            if (!a.isEmpty()) return true;
        }
        return false;
    }

    private static void dfs(Vertex vertex, Map<Vertex, Boolean> info, Graph graph, Set<Vertex> currentEven,
                            Set<Vertex> currentUneven, boolean isEvenCount) {
        if (isEvenCount) currentEven.add(vertex);
        else currentUneven.add(vertex);
        info.put(vertex, true);
        for (Vertex neighbour : graph.getNeighbors(vertex)) {
            if (!info.get(neighbour)) {
                isEvenCount = !isEvenCount;
                dfs(neighbour, info, graph, currentEven, currentUneven, isEvenCount);
                isEvenCount = !isEvenCount;
            }
        }
    }

    public static Set<Vertex> largestIndependentVertexSet(Graph graph) {
        if (isCyclical(graph)) throw new IllegalArgumentException();//O(V+E)

        HashMap<Vertex, Boolean> info = new HashMap<>();
        HashSet<Vertex> result = new LinkedHashSet<>();
        LinkedHashSet<Vertex> currentEven = new LinkedHashSet<>();
        LinkedHashSet<Vertex> currentUneven = new LinkedHashSet<>();
        boolean isEvenCount;

        for (Vertex vertex : graph.getVertices()) info.put(vertex, false);

        for (Vertex vertex : graph.getVertices())
            if (!info.get(vertex)) {
                dfs(vertex, info, graph, currentEven, currentUneven, true);
                result.addAll(currentEven.size() >= currentUneven.size() ? currentEven : currentUneven);
                currentEven.clear();
                currentUneven.clear();
            }
        return result;
    }//Ресурсоемкость O(V),трудоемкость O(V+E)

    /**
     * Наидлиннейший простой путь.
     * Сложная
     * <p>
     * Дан граф (получатель). Найти в нём простой путь, включающий максимальное количество рёбер.
     * Простым считается путь, вершины в котором не повторяются.
     * Если таких путей несколько, вернуть любой из них.
     * <p>
     * Пример:
     * <p>
     * G -- H
     * |    |
     * A -- B -- C -- D
     * |    |    |    |
     * E    F -- I    |
     * |              |
     * J ------------ K
     * <p>
     * Ответ: A, E, J, K, D, C, H, G, B, F, I
     */
    private static void modifiedDfs(Vertex vertex, Graph graph, ArrayList<Vertex> current,
                                    ArrayList<Vertex> result) {
        if (!current.contains(vertex)) {
            current.add(vertex);
            for (Vertex neighbour : graph.getNeighbors(vertex))//E
                if (!current.contains(neighbour)) modifiedDfs(neighbour, graph, current, result);//E
        }
        if (current.size() > result.size()) {
            result.clear();
            result.addAll(current);
        }
        if (current.size() != 1) current.remove(current.size() - 1);

    }

    private static Path createPathFromVertexList(List<Vertex> vertices, Graph graph) {
        Path resultPath = new Path(vertices.get(0));
        if (vertices.size() > 1)
            for (int i = 1; i < vertices.size(); i++) resultPath = new Path(resultPath, graph, vertices.get(i));
        return resultPath;
    }

    public static Path longestSimplePath(Graph graph) {
        if (graph.getVertices().isEmpty()) return new Path();
        ArrayList<Vertex> result = new ArrayList<>();
        ArrayList<Vertex> current;
        for (Vertex vertex : graph.getVertices()) {//V
            current = new ArrayList<>();
            if (result.size() != graph.getVertices().size()) modifiedDfs(vertex, graph, current, result);
            else return createPathFromVertexList(result, graph);
        }
        return createPathFromVertexList(result, graph);
        //Итого:Ресурсоемкость:O(2V),Трудоемкость:O(V*E.pow(V)).
    }

    /**
     * Балда
     * Сложная
     * <p>
     * Задача хоть и не использует граф напрямую, но решение базируется на тех же алгоритмах -
     * поэтому задача присутствует в этом разделе
     * <p>
     * В файле с именем inputName задана матрица из букв в следующем формате
     * (отдельные буквы в ряду разделены пробелами):
     * <p>
     * И Т Ы Н
     * К Р А Н
     * А К В А
     * <p>
     * В аргументе words содержится множество слов для поиска, например,
     * ТРАВА, КРАН, АКВА, НАРТЫ, РАК.
     * <p>
     * Попытаться найти каждое из слов в матрице букв, используя правила игры БАЛДА,
     * и вернуть множество найденных слов. В данном случае:
     * ТРАВА, КРАН, АКВА, НАРТЫ
     * <p>
     * И т Ы Н     И т ы Н
     * К р а Н     К р а н
     * А К в а     А К В А
     * <p>
     * Все слова и буквы -- русские или английские, прописные.
     * В файле буквы разделены пробелами, строки -- переносами строк.
     * Остальные символы ни в файле, ни в словах не допускаются.
     */
    static public Set<String> baldaSearcher(String inputName, Set<String> words) {
        throw new NotImplementedError();
    }
}
