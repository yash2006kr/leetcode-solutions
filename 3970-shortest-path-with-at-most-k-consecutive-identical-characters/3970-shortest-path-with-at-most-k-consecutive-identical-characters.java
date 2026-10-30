import java.util.*;

class Solution {
    public int shortestPath(int n, int[][] edges, String labels, int k) {
        // Build adjacency list (CSR format)
        int m = edges.length;
        int[] head = new int[n + 1];
        for (int[] e : edges) head[e[0] + 1]++;
        for (int i = 0; i < n; i++) head[i + 1] += head[i];
        int[] to = new int[m], wt = new int[m];
        int[] pos = Arrays.copyOf(head, n);
        for (int[] e : edges) {
            int p = pos[e[0]]++;
            to[p] = e[1];
            wt[p] = e[2];
        }

        char[] lb = labels.toCharArray();
        int W = k + 1; // run length ranges 1..k
        int[] dist = new int[n * W];
        Arrays.fill(dist, Integer.MAX_VALUE);

        // Encode (dist, state) into one long: dist in high bits, state in low 32 bits
        PriorityQueue<Long> pq = new PriorityQueue<>();
        int start = 0 * W + 1;
        dist[start] = 0;
        pq.offer((long) start);

        while (!pq.isEmpty()) {
            long top = pq.poll();
            int d = (int) (top >>> 32);
            int state = (int) (top & 0xFFFFFFFFL);
            if (d > dist[state]) continue;

            int u = state / W, run = state % W;
            if (u == n - 1) return d; // first time we pop the target is optimal

            for (int i = head[u]; i < head[u + 1]; i++) {
                int v = to[i];
                int nr = (lb[v] == lb[u]) ? run + 1 : 1;
                if (nr > k) continue;
                int ns = v * W + nr;
                int nd = d + wt[i];
                if (nd < dist[ns]) {
                    dist[ns] = nd;
                    pq.offer(((long) nd << 32) | ns);
                }
            }
        }
        return -1;
    }
}