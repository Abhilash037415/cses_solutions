import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Monsters1 {

    static char[][] grid;
    static int[][] monsterTime;
    static int[] dir1 = { 0, 0, -1, 1 };
    static int[] dir2 = { -1, 1, 0, 0 };
    static char[] move = { 'L', 'R', 'U', 'D' };
    static char[][] moves;
    static int[][] parentr;
    static int[][] parentc;
    static ArrayDeque<int[]> monsters = new ArrayDeque<>();
    static int[][] dist;
    static int n;
    static int m;

    public static void bfs(int startr, int startc) {

        ArrayDeque<int[]> deque = new ArrayDeque<>();
        boolean visited[][] = new boolean[grid.length][grid[0].length];

        deque.add(new int[] { startr, startc });
        visited[startr][startc] = true;
        dist[startr][startc] = 0;
        while (!deque.isEmpty()) {

            int[] curr = deque.poll();
            int r = curr[0];
            int c = curr[1];

            if (r == n - 1 || r == 0 || c == m - 1 || c == 0) {
                printPath(r, c, startr, startc);
                return;
            }

            for (int i = 0; i < 4; i++) {
                int nr = dir1[i] + r;
                int nc = dir2[i] + c;

                if (nr < 0 || nc < 0 || nr >= n || nc >= m || monsterTime[nr][nc] <= dist[r][c] + 1
                        || visited[nr][nc] || grid[nr][nc] == '#') {
                    continue;
                }

                visited[nr][nc] = true;
                parentr[nr][nc] = r;
                parentc[nr][nc] = c;
                dist[nr][nc] = dist[r][c] + 1;
                moves[nr][nc] = move[i];
                deque.add(new int[] { nr, nc });
            }

        }

        System.out.println("NO");
    }

    public static void printPath(int row, int col, int startr, int startc) {
        StringBuilder sb = new StringBuilder();
        while (!(row == startr && col == startc)) {
            sb.append(moves[row][col]);
            int tempr = parentr[row][col];
            int tempc = parentc[row][col];

            row = tempr;
            col = tempc;
        }
        System.out.println("YES");
        System.out.println(sb.length());
        System.out.println(sb.reverse().toString());
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        moves = new char[n][m];
        parentr = new int[n][m];
        parentc = new int[n][m];
        dist = new int[n][m];

        grid = new char[n][m];
        monsterTime = new int[n][m];

        for (int[] row : monsterTime) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        int startr = 0;
        int startc = 0;
        grid = new char[n][m];

        for (int i = 0; i < n; i++) {
            String temp = br.readLine();
            for (int j = 0; j < m; j++) {
                grid[i][j] = temp.charAt(j);
                if (grid[i][j] == 'M') {
                    monsterTime[i][j] = 0;
                    monsters.add(new int[] { i, j });
                }
                if (grid[i][j] == 'A') {
                    startr = i;
                    startc = j;
                }
            }
        }

        if (startr == 0 || startc == 0 || startr == n - 1 || startc == m - 1) {
            System.out.println("YES");
            System.out.println(0);
            return;
        }

        while (!monsters.isEmpty()) {
            int[] curr = monsters.poll();
            int r = curr[0];
            int c = curr[1];

            for (int i = 0; i < 4; i++) {
                int nr = r + dir1[i];
                int nc = c + dir2[i];

                if (nr < 0 || nc < 0 || nr >= n || nc >= m || grid[nr][nc] == '#') {
                    continue;
                }

                if (monsterTime[nr][nc] > monsterTime[r][c] + 1) {
                    monsterTime[nr][nc] = monsterTime[r][c] + 1;
                    monsters.add(new int[] { nr, nc });
                }
            }
        }

        bfs(startr, startc);

    }
}
