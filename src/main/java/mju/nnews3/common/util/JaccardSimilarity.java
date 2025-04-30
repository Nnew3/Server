package mju.nnews3.common.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class JaccardSimilarity {

    public static Result compute(String s1, String s2) {
        Set<String> set1 = new HashSet<>(Arrays.asList(s1.split("\\s+")));
        Set<String> set2 = new HashSet<>(Arrays.asList(s2.split("\\s+")));

        Set<String> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);

        Set<String> union = new HashSet<>(set1);
        union.addAll(set2);

        double similarity = union.isEmpty() ? 0.0 : (double) intersection.size() / union.size();
        return new Result(similarity, intersection);
    }

    public record Result(double similarity, Set<String> commonWords) {
    }
}
