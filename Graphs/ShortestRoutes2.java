import java.io.InputStream;
import java.io.IOException;

public class ShortestRoutes2 {
    static final long INF = 1_000_000_000_000_000L; 

    public static void main(String[] args) throws IOException {
        FastScanner scanner = new FastScanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int q = scanner.nextInt();

        long[][] dp = new long[n + 1][n + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (i != j) {
                    dp[i][j] = INF;
                }
            }
        }

        for (int i = 0; i < m; i++) {
            int u = scanner.nextInt();
            int v = scanner.nextInt();
            long w = scanner.nextLong();

            if (w < dp[u][v]) {
                dp[u][v] = w;
                dp[v][u] = w;
            }
        }

        for (int k = 1; k <= n; k++) {
            for (int i = 1; i <= n; i++) {
                long ik = dp[i][k];
                if (ik >= INF) continue;

                for (int j = 1; j <= n; j++) {
                    long newDist = ik + dp[k][j];
                    if (newDist < dp[i][j]) {
                        dp[i][j] = newDist;
                    }
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            int start = scanner.nextInt();
            int end = scanner.nextInt();
            long ans = dp[start][end];
            sb.append(ans >= INF ? -1 : ans).append('\n');
        }
        System.out.print(sb);
    }

    static class FastScanner {
        private final InputStream stream;
        private final byte[] buf = new byte[1024];
        private int head = 0;
        private int tail = 0;

        public FastScanner(InputStream stream) {
            this.stream = stream;
        }

        private int read() throws IOException {
            if (head >= tail) {
                head = 0;
                tail = stream.read(buf, 0, buf.length);
                if (tail <= 0) return -1;
            }
            return buf[head++];
        }

        public int nextInt() throws IOException {
            int c = read();
            while (c <= 32) {
                if (c == -1) return -1;
                c = read();
            }
            int res = 0;
            while (c > 32) {
                if (c < '0' || c > '9') throw new RuntimeException();
                res = res * 10 + c - '0';
                c = read();
            }
            return res;
        }

        public long nextLong() throws IOException {
            int c = read();
            while (c <= 32) {
                if (c == -1) return -1;
                c = read();
            }
            long res = 0;
            while (c > 32) {
                if (c < '0' || c > '9') throw new RuntimeException();
                res = res * 10 + c - '0';
                c = read();
            }
            return res;
        }
    }
}