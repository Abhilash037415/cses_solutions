import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

public class Monsters {
    static int n, m;
    static int[][] monsterTime;
    static char[][] grid;
    static int[][] dist;
    static int[] dr = { 0, 0, -1, 1 };
    static int[] dc = { -1, 1, 0, 0 };
    static char[] dir = { 'L', 'R', 'U', 'D' };

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        grid = new char[n][m];
        Queue<int[]> monsterQueue = new ArrayDeque<>();
        monsterTime = new int[n][m];
        dist = new int[n][m];
        for (int[] row : monsterTime) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }
        int sr = 0, sc = 0;
        for (int i = 0; i < n; i++) {
            String s = br.readLine();
            for (int j = 0; j < m; j++) {
                grid[i][j] = s.charAt(j);
                if (grid[i][j] == 'M') {
                    monsterQueue.add(new int[] { i, j });
                    monsterTime[i][j] = 0;
                }
                if (grid[i][j] == 'A') {
                    sr = i;
                    sc = j;
                }
            }
        }

        if (sr == 0 || sc == 0 || sr == n - 1 || sc == m - 1) {
            System.out.println("YES");
            System.out.println(0);
            return;
        }

        int[][] parentr = new int[n][m];
        int[][] parentc = new int[n][m];
        char[][] move = new char[n][m];

        while (!monsterQueue.isEmpty()) {
            int[] curr = monsterQueue.poll();
            int r = curr[0], c = curr[1];

            for (int k = 0; k < 4; k++) {
                int nr = r + dr[k], nc = c + dc[k];

                if (nr < 0 || nc < 0 || nr >= n || nc >= m || grid[nr][nc] == '#') {
                    continue;
                }

                if (monsterTime[nr][nc] > monsterTime[r][c] + 1) {
                    monsterTime[nr][nc] = monsterTime[r][c] + 1;
                    monsterQueue.add(new int[] { nr, nc });
                }
            }
        }

        Queue<int[]> p = new ArrayDeque<>();
        p.add(new int[] { sr, sc });
        boolean visited[][] = new boolean[n][m];
        visited[sr][sc] = true;
        dist[sr][sc] = 0;
        while (!p.isEmpty()) {
            int[] current = p.poll();
            int r = current[0], c = current[1];
            if (r == 0 || c == 0 || r == n - 1 || c == m - 1) {
                printPath(r, c, parentr, parentc, move);
                return;
            }
            for (int k = 0; k < 4; k++) {
                int nr = r + dr[k], nc = c + dc[k];

                if (nr < 0 || nc < 0 || nr >= n || nc >= m || grid[nr][nc] == '#' || visited[nr][nc]) {
                    continue;
                }

                if (dist[r][c] + 1 >= monsterTime[nr][nc]) {
                    continue;
                }

                visited[nr][nc] = true;
                dist[nr][nc] = dist[r][c] + 1;
                parentr[nr][nc] = r;
                parentc[nr][nc] = c;
                move[nr][nc] = dir[k];
                p.offer(new int[] { nr, nc });
            }
        }
        System.out.println("NO");
    }

    static void printPath(int r, int c, int[][] parentr, int[][] parentc, char[][] move) {
        StringBuilder sb = new StringBuilder();

        while (grid[r][c] != 'A') {
            sb.append(move[r][c]);
            int nr = parentr[r][c];
            int nc = parentc[r][c];
            r = nr;
            c = nc;
        }
        sb.reverse();
        System.out.println("YES");
        System.out.println(sb.length());
        System.out.println(sb.toString());
    }
}