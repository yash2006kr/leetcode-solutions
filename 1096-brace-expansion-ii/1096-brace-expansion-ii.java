import java.util.*;

class Solution {
    private String exp;
    private int i;

    public List<String> braceExpansionII(String expression) {
        exp = expression;
        i = 0;
        Set<String> result = parseUnion();
        List<String> list = new ArrayList<>(result);
        Collections.sort(list);
        return list;
    }

    // union := concat (',' concat)*
    private Set<String> parseUnion() {
        Set<String> result = new HashSet<>(parseConcat());
        while (i < exp.length() && exp.charAt(i) == ',') {
            i++; // skip ','
            result.addAll(parseConcat());
        }
        return result;
    }

    // concat := factor+  (cartesian product of factors)
    private Set<String> parseConcat() {
        List<Set<String>> factors = new ArrayList<>();
        while (i < exp.length() && exp.charAt(i) != ',' && exp.charAt(i) != '}') {
            factors.add(parseFactor());
        }

        Set<String> result = new HashSet<>();
        result.add("");
        for (Set<String> factor : factors) {
            Set<String> next = new HashSet<>();
            for (String prefix : result) {
                for (String suffix : factor) {
                    next.add(prefix + suffix);
                }
            }
            result = next;
        }
        return result;
    }

    // factor := '{' union '}' | letters
    private Set<String> parseFactor() {
        if (exp.charAt(i) == '{') {
            i++; // skip '{'
            Set<String> result = parseUnion();
            i++; // skip '}'
            return result;
        } else {
            int start = i;
            while (i < exp.length() && Character.isLowerCase(exp.charAt(i))) {
                i++;
            }
            return new HashSet<>(Collections.singletonList(exp.substring(start, i)));
        }
    }
}