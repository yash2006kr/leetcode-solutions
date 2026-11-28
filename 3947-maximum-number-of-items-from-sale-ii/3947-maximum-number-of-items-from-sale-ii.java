import java.util.*;

class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;
        int maxFactor = 0;
        for (int[] item : items) {
            if (item[0] > maxFactor) {
                maxFactor = item[0];
            }
        }

        // Count how many items have each factor value
        long[] freq = new long[maxFactor + 1];
        for (int[] item : items) {
            freq[item[0]]++;
        }

        // For each factor f, total items with factor divisible by f (including f itself)
        long[] multCount = new long[maxFactor + 1];
        for (int f = 1; f <= maxFactor; f++) {
            for (int m = f; m <= maxFactor; m += f) {
                multCount[f] += freq[m];
            }
        }

        // Build list of [price, gain], where gain excludes self (j != i)
        long minPrice = Long.MAX_VALUE;
        long[][] list = new long[n][2];
        for (int i = 0; i < n; i++) {
            int f = items[i][0];
            long p = items[i][1];
            long gain = multCount[f] - 1; // exclude item i itself
            list[i][0] = p;
            list[i][1] = gain;
            if (p < minPrice) {
                minPrice = p;
            }
        }

        // Sort items by price ascending
        Arrays.sort(list, Comparator.comparingLong(a -> a[0]));

        long remBudget = budget;
        long totalItems = 0;

        // Greedily take value-2 units when price < 2 * minPrice
        for (int i = 0; i < n; i++) {
            long p = list[i][0];
            long gain = list[i][1];

            if (p >= 2 * minPrice) {
                break;
            }
            if (gain > 0 && remBudget >= p) {
                long units = Math.min(gain, remBudget / p);
                totalItems += units * 2;
                remBudget -= units * p;
            }
        }

        // Spend remaining budget on cheapest item (value-1 each)
        if (remBudget >= minPrice) {
            totalItems += remBudget / minPrice;
        }

        return (int) totalItems;
    }
}