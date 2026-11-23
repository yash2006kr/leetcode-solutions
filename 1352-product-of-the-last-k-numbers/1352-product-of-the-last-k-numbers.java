import java.util.ArrayList;
import java.util.List;

class ProductOfNumbers {
    private final List<Integer> prefixes;

    public ProductOfNumbers() {
        prefixes = new ArrayList<>();
        prefixes.add(1);
    }

    public void add(int num) {
        if (num == 0) {
            prefixes.clear();
            prefixes.add(1);
        } else {
            int last = prefixes.get(prefixes.size() - 1);
            prefixes.add(last * num);
        }
    }

    public int getProduct(int k) {
        int n = prefixes.size();
        if (k >= n) {
            return 0;
        }
        return prefixes.get(n - 1) / prefixes.get(n - 1 - k);
    }
}