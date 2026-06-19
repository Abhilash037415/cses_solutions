import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class HighScore {
    static class FastScanner {
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;

        private int read() throws IOException {
            if (ptr >= len) {
                len = System.in.read(buffer);
                ptr = 0;
                if (len <= 0)
                    return -1;
            }
            return buffer[ptr++];
        }

        int nextInt() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= ' ');

            int sign = 1;
            if (c == '-') {
                sign = -1;
                c = read();
            }

            int num = 0;
            while (c > ' ') {
                num = num * 10 + (c - '0');
                c = read();
            }

            return num * sign;
        }
    }

    static class Edge {
        int destination, weight;

        Edge(int destination, int weight) {
            this.destination = destination;
            this.weight = weight;
        }
    }

    public static HashMap<Integer, List<Edge>> map;

    public static long INF = (long) 1e18;

    public static void main(String args[]) throws IOException {
        FastScanner input = new FastScanner();
        map = new HashMap<>();
        int n = input.nextInt();
        int m = input.nextInt();

        int[][] edges = new int[m][3];
        for (int i = 0; i < m; i++) {
            edges[i][0] = input.nextInt();
            edges[i][1] = input.nextInt();
            edges[i][2] = -(input.nextInt());

            map.computeIfAbsent(edges[i][0], a -> new ArrayList<>()).add(new Edge(edges[i][1], edges[i][2]));
        }

        long[] dist = new long[n + 1];
        Arrays.fill(dist, INF);
        dist[1] = 0;
        for (int i = 0; i < n - 1; i++) {
            for (int[] edge : edges) {
                int u = edge[0];
                int v = edge[1];
                int w = edge[2];

                if (dist[u] == INF)
                    continue;

                if (dist[u] + w < dist[v]) {
                    dist[v] = dist[u] + w;
                }
            }
        }

        boolean[] affected = new boolean[n + 1];

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            int w = edge[2];

            if (dist[u] == INF)
                continue;

            if (dist[u] + w < dist[v]) {
                affected[v] = true;
            }
        }

        ArrayDeque<Integer> queue = new ArrayDeque<>();
        boolean[] visited = new boolean[n + 1];
        for (int i = 1; i < affected.length; i++) {
            if (affected[i]) {
                queue.add(i);
                visited[i] = true;
            }
        }

        while (!queue.isEmpty()) {
            int source = queue.poll();
            if (source == n) {
                System.out.println(-1);
                return;
            }
            if (!map.containsKey(source))
                continue;

            for (Edge edge : map.get(source)) {
                if (visited[edge.destination]) {
                    continue;
                }

                visited[edge.destination] = true;
                queue.add(edge.destination);
            }
        }

        System.out.println(-dist[n]);
    }
}
