class Solution {

    static class Node {
        int[] remain = new int[5];
        int prod = 1;

        Node() {}

        Node(int val, int k) {
            val %= k;
            this.prod = val;
            this.remain[val] = 1;
        }
    }

    static class SegmentTree {
        private final int n;
        private final int k;
        private final Node[] tree;

        public SegmentTree(int[] nums, int k) {
            this.n = nums.length;
            this.k = k;
            this.tree = new Node[4 * n];
            build(nums, 0, 0, n - 1);
        }

        private void build(int[] nums, int cur, int left, int right) {
            if (left == right) {
                tree[cur] = new Node(nums[left], k);
                return;
            }
            int mid = left + (right - left) / 2;
            build(nums, 2 * cur + 1, left, mid);
            build(nums, 2 * cur + 2, mid + 1, right);
            tree[cur] = merge(tree[2 * cur + 1], tree[2 * cur + 2]);
        }

        public void update(int index, int val) {
            update(0, 0, n - 1, index, val % k);
        }

        private void update(int cur, int lo, int hi, int index, int val) {
            if (lo == hi) {
                tree[cur] = new Node(val, k);
                return;
            }
            int mid = lo + (hi - lo) / 2;
            if (index <= mid) {
                update(2 * cur + 1, lo, mid, index, val);
            } else {
                update(2 * cur + 2, mid + 1, hi, index, val);
            }
            tree[cur] = merge(tree[2 * cur + 1], tree[2 * cur + 2]);
        }

        public Node query(int i, int j) {
            return query(0, 0, n - 1, i, j);
        }

        private Node query(int cur, int lo, int hi, int i, int j) {
            if (i <= lo && hi <= j) {
                return tree[cur];
            }
            int mid = lo + (hi - lo) / 2;
            if (j <= mid) {
                return query(2 * cur + 1, lo, mid, i, j);
            }
            if (i > mid) {
                return query(2 * cur + 2, mid + 1, hi, i, j);
            }

            Node leftRes = query(2 * cur + 1, lo, mid, i, j);
            Node rightRes = query(2 * cur + 2, mid + 1, hi, i, j);
            return merge(leftRes, rightRes);
        }

        private Node merge(Node left, Node right) {
            Node res = new Node();
            res.prod = (left.prod * right.prod) % k;

            for (int r = 0; r < k; r++) {
                res.remain[r] = left.remain[r];
            }

            for (int r = 0; r < k; r++) {
                int newRem = (r * left.prod) % k;
                res.remain[newRem] += right.remain[r];
            }

            return res;
        }
    }

    public int[] resultArray(int[] nums, int k, int[][] queries) {
        int n = nums.length;
        SegmentTree segmentTree = new SegmentTree(nums, k);
        int[] result = new int[queries.length];

        for (int q = 0; q < queries.length; q++) {
            int index = queries[q][0];
            int value = queries[q][1];
            int start = queries[q][2];
            int x = queries[q][3];

            // Persistent point update
            segmentTree.update(index, value);

            // Range query from start to n - 1
            Node res = segmentTree.query(start, n - 1);
            result[q] = res.remain[x];
        }

        return result;
    }
}