import java.util.*;

class Solution {
    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;
        List<Integer>[] origTree = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            origTree[i] = new ArrayList<>();
        }
        for (int i = 1; i < n; i++) {
            origTree[parent[i]].add(i);
        }

        int[] newParent = Arrays.copyOf(parent, n);
        int[] lastSeen = new int[26];
        Arrays.fill(lastSeen, -1);

        dfsReassign(0, s, origTree, lastSeen, newParent);

        List<Integer>[] finalTree = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            finalTree[i] = new ArrayList<>();
        }
        for (int i = 1; i < n; i++) {
            finalTree[newParent[i]].add(i);
        }

        int[] ans = new int[n];
        dfsSize(0, finalTree, ans);
        return ans;
    }

    private void dfsReassign(int u, String s, List<Integer>[] origTree, int[] lastSeen, int[] newParent) {
        int c = s.charAt(u) - 'a';
        int prev = lastSeen[c];

        if (u != 0 && prev != -1) {
            newParent[u] = prev;
        }

        lastSeen[c] = u;
        for (int v : origTree[u]) {
            dfsReassign(v, s, origTree, lastSeen, newParent);
        }
        lastSeen[c] = prev;
    }

    private int dfsSize(int u, List<Integer>[] finalTree, int[] ans) {
        int size = 1;
        for (int v : finalTree[u]) {
            size += dfsSize(v, finalTree, ans);
        }
        ans[u] = size;
        return size;
    }
}