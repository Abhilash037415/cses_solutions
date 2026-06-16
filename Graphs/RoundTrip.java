import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

public class RoundTrip {
    static HashMap<Integer, List<Integer>> map;
    static boolean[] visited;
    static int[] parents;

    public static boolean dfs(int source) {
        visited[source] = true;

        if (!map.containsKey(source))
            return false;

        for (int i : map.get(source)) {

            if (i == parents[source]) {
                continue;
            }

            if (!visited[i]) {
                parents[i] = source;
                if (dfs(i)) {
                    return true;
                }
            } else {
                printCycle(source, i);
                return true;
            }

        }

        return false;
    }

    public static void printCycle(int source, int current) {
        List<Integer> list = new ArrayList<>();

        list.add(current);

        int temp = source;

        while (temp != current) {
            list.add(temp);
            temp = parents[temp];
        }

        list.add(current);
        Collections.reverse(list);
        StringBuilder sb = new StringBuilder();
        for (int i : list) {
            sb.append(i).append(" ");
        }
        System.out.println(list.size());
        System.out.println(sb);
    }

    public static void addEdge(int source, int destination) {
        if (!map.containsKey(source)) {
            map.put(source, new ArrayList<>());
        }
        map.get(source).add(destination);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        map = new HashMap<>();
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        visited = new boolean[n + 1];
        parents = new int[n + 1];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            addEdge(u, v);
            addEdge(v, u);
        }

        for (int i = 1; i <= n; i++) {
            if (!visited[i]) {
                if (dfs(i)) {
                    return;
                }
            }
        }

        System.out.println("IMPOSSIBLE");

    }
}
