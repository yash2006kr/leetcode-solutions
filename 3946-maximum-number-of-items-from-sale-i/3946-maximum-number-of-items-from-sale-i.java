class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        final int MAX_F = 1500;
        int n = items.length;

        int[] factorCount = new int[MAX_F + 1];
        int minPrice = Integer.MAX_VALUE;
        for (int[] item : items) {
            factorCount[item[0]]++;
            minPrice = Math.min(minPrice, item[1]);
        }

        // bonus[i] = number of distinct items j != i such that items[i][0] divides items[j][0]
        int[] bonus = new int[n];
        for (int i = 0; i < n; i++) {
            int f = items[i][0];
            int totalDivisible = 0;
            for (int mult = f; mult <= MAX_F; mult += f) {
                totalDivisible += factorCount[mult];
            }
            bonus[i] = totalDivisible - 1;
        }

        // 0/1 knapsack over first-time item activations: cost = price, value = 1 + bonus[i]
        int[] dp = new int[budget + 1];
        for (int i = 0; i < n; i++) {
            int price = items[i][1];
            int value = 1 + bonus[i];
            if (price <= budget) {
                for (int b = budget; b >= price; b--) {
                    dp[b] = Math.max(dp[b], dp[b - price] + value);
                }
            }
        }

        // Spend any remaining budget on the cheapest item (1 copy per minPrice)
        int ans = 0;
        for (int b = 0; b <= budget; b++) {
            int extraCopies = (budget - b) / minPrice;
            ans = Math.max(ans, dp[b] + extraCopies);
        }

        return ans;
    }
}