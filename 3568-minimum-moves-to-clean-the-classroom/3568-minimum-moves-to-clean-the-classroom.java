import java.util.*;

class Solution {
    public int minMoves(String[] classroom, int energy) {
        int m = classroom.length, n = classroom[0].length();
        char[][] grid = new char[m][n];
        for (int i = 0; i < m; i++) grid[i] = classroom[i].toCharArray();

        int sr = -1, sc = -1;
        Map<Integer, Integer> litterBit = new HashMap<>();
        int litterCount = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 'S') { sr = i; sc = j; }
                else if (grid[i][j] == 'L') litterBit.put(i * n + j, litterCount++);
            }
        }
        if (litterCount == 0) return 0;

        int full = (1 << litterCount) - 1;
        int[][] dirs = {{-1,0},{1,0},{0,-1},{0,1}};

        // best[r][c][mask] = max energy ever recorded arriving at that state
        int[][][] best = new int[m][n][1 << litterCount];
        for (int[][] a : best) for (int[] b : a) Arrays.fill(b, -1);

        best[sr][sc][0] = energy;
        Deque<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{sr, sc, 0, 0}); // r, c, mask, moves

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int r = cur[0], c = cur[1], mask = cur[2], moves = cur[3];

            if (mask == full) return moves;

            int curEnergy = best[r][c][mask];
            if (curEnergy <= 0) continue; // stuck, can't move further

            for (int[] d : dirs) {
                int nr = r + d[0], nc = c + d[1];
                if (nr < 0 || nr >= m || nc < 0 || nc >= n) continue;
                char ch = grid[nr][nc];
                if (ch == 'X') continue;

                int newEnergy = curEnergy - 1;
                if (newEnergy < 0) continue;

                int newMask = mask;
                Integer bit = litterBit.get(nr * n + nc);
                if (bit != null) newMask = mask | (1 << bit);

                if (ch == 'R') newEnergy = energy; // full reset regardless of level

                if (newEnergy > best[nr][nc][newMask]) {
                    best[nr][nc][newMask] = newEnergy;
                    queue.offer(new int[]{nr, nc, newMask, moves + 1});
                }
            }
        }
        return -1;
    }
}