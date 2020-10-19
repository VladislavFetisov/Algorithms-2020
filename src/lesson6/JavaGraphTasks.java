package lesson6;

import kotlin.NotImplementedError;

import java.util.*;

import static lesson6.Graph.Edge;
import static lesson6.Graph.Vertex;

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
     * G -- H -- J
     * |
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
    public static Set<Vertex> largestIndependentVertexSet(Graph graph) {
        throw new NotImplementedError();
    }

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
     *      G -- H
     *      |    |
     * A -- B -- C -- D
     * |    |    |    |
     * E    F -- I    |
     * |              |
     * J ------------ K
     * <p>
     * Ответ: A, E, J, K, D, C, H, G, B, F, I
     */
    private static void modifiedDfs(Vertex vertex, Graph graph, ArrayList<Vertex> currentPath,
                                    ArrayList<Vertex> resultPath) {
        if (!currentPath.contains(vertex)) {
            currentPath.add(vertex);
            for (Vertex neighbour : graph.getNeighbors(vertex)) {
                if (!currentPath.contains(neighbour)) modifiedDfs(neighbour, graph, currentPath, resultPath);
            }
        }
        if (currentPath.size() > resultPath.size()) {
            resultPath.clear();
            resultPath.addAll(currentPath);
        }
        if (currentPath.size() != 1) currentPath.remove(currentPath.size() - 1);

    }

    public static Path longestSimplePath(Graph graph) {
        if (graph.getVertices().isEmpty()) return new Path();
        ArrayList<Vertex> resultPath = new ArrayList<>();
        ArrayList<Vertex> currentPath;
        for (Vertex vertex : graph.getVertices()) {
            currentPath = new ArrayList<>();
            modifiedDfs(vertex, graph, currentPath, resultPath);
        }

        Path result = new Path(resultPath.get(0));
        if (resultPath.size() > 1) {
            for (int i = 1; i < resultPath.size(); i++) {
                result = new Path(result, graph, resultPath.get(i));
            }
        }
        return result;
    }
        /*HashMap<VertexPair, ArrayList<VertexPair>> adjacencyList = new HashMap<>();
        ArrayList<VertexData> dataArray = new ArrayList<>();

        int i = 0;
        for (Vertex vertex : graph.getVertices()) {
            adjacencyList.put(new VertexPair(vertex, i), new ArrayList<>());
            dataArray.add(new VertexData(false, vertex));
            i++;
        }
        for (Map.Entry<VertexPair, ArrayList<VertexPair>> entry : adjacencyList.entrySet()) {
            for (Vertex neighbour : graph.getNeighbors(entry.getKey().vertex)) {
                for (Map.Entry<VertexPair, ArrayList<VertexPair>> entry1 : adjacencyList.entrySet()) {
                    if (entry1.getKey().vertex == neighbour) {
                        entry.getValue().add(entry1.getKey());
                        break;
                    }
                }//(A,i)=(B,j),(C,k)-Список смежности,adjacencyList;i,j,k-индексы в массиве dataArray
            }
        }
        int c;
        Integer max = -1;
        for (Map.Entry<VertexPair, ArrayList<VertexPair>> entry : adjacencyList.entrySet()) {
            currentPath = new ArrayList<>();
            c = -1;
            currentPath.add(entry.getKey().vertex);
            modifiedDfs(entry.getKey(), c, max, resultPath, currentPath, dataArray, adjacencyList);
        }
        Path result = new Path();
        for (Vertex vertex : resultPath) result = new Path(result, graph, vertex);
        return result;

       */
/*VertexPair vertexPair, int c, Integer max, ArrayList<Vertex> resultPath,
                                    ArrayList<Vertex> currentPath, List<VertexData> dataArray, Map<VertexPair,
            ArrayList<VertexPair>> adjacencyList c++;
        dataArray.get(vertexPair.index).isVisited = true;
        System.out.println(dataArray);
        for (VertexPair neighbour : adjacencyList.get(vertexPair)) {
            if (!dataArray.get(neighbour.index).isVisited)
                modifiedDfs(neighbour, c, max, resultPath, currentPath, dataArray, adjacencyList);
        }
        if (currentPath.size() > resultPath.size()) {
            resultPath.clear();
            resultPath.addAll(currentPath);
        }
        dataArray.get(vertexPair.index).isVisited = false;
        currentPath.remove(currentPath.size() - 1);
        */

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
