import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class ShortestRoutes {

    static class Pair {
        int destination;
        long weight;

        Pair(int value, long weight) {
            this.destination = value;
            this.weight = weight;
        }
    }

    static HashMap<Integer, List<Pair>> map;
    static long[] shortest;

    public static void bfs() {
        PriorityQueue<Pair> deque = new PriorityQueue<>((a, b) -> {
            return Long.compare(a.weight, b.weight);
        });

        deque.offer(new Pair(1, 0));

        while (!deque.isEmpty()) {
            Pair pair = deque.poll();

            int source = pair.destination;
            long weight = pair.weight;

            if (weight != shortest[source]) {
                continue;
            }

            if (!map.containsKey(source)) {
                continue;
            }

            for (Pair p : map.get(source)) {
                long newWeight = weight + p.weight;

                if (newWeight >= shortest[p.destination]) {
                    continue;
                }

                shortest[p.destination] = newWeight;
                deque.add(new Pair(p.destination, newWeight));
            }
        }

        printAnswer();
    }

    public static void printAnswer() {
        StringBuilder sb = new StringBuilder();

        for (int i = 1; i < shortest.length; i++) {
            sb.append(shortest[i]).append(" ");
        }

        System.out.println(sb.toString().trim());
    }

    public static void addEdge(int source, int destination, int weight) {
        if (!map.containsKey(source)) {
            map.put(source, new ArrayList<>());
        }
        map.get(source).add(new Pair(destination, weight));
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());

        map = new HashMap<>();

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        shortest = new long[n + 1];
        Arrays.fill(shortest, Long.MAX_VALUE);

        shortest[1] = 0;

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());

            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());

            addEdge(u, v, weight);
        }

        bfs();
    }
}