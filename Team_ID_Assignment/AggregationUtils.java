import java.util.List;
import java.util.regex.Pattern;

public class AggregationUtils {

    public static double calculateAverageFromText(List<String> rawLines) {
        return rawLines.stream()
            .flatMap(line -> Pattern.compile("-?\\d+(\\.\\d+)?").matcher(line).results())
            .mapToDouble(match -> Double.parseDouble(match.group()))
            .average()
            .orElse(0.0);
    }

    public static long countInversions(List<Integer> list) {
        if (list.isEmpty()) return 0;
        return mergeSortAndCount(list.stream().mapToInt(i->i).toArray(), 0, list.size() - 1);
    }

    private static long mergeSortAndCount(int[] arr, int l, int r) {
        long count = 0;
        if (l < r) {
            int m = (l + r) / 2;
            count += mergeSortAndCount(arr, l, m);
            count += mergeSortAndCount(arr, m + 1, r);
            count += mergeAndCount(arr, l, m, r);
        }
        return count;
    }

    private static long mergeAndCount(int[] arr, int l, int m, int r) {
        int[] left = java.util.Arrays.copyOfRange(arr, l, m + 1);
        int[] right = java.util.Arrays.copyOfRange(arr, m + 1, r + 1);
        int i = 0, j = 0, k = l;
        long swaps = 0;
        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) arr[k++] = left[i++];
            else { arr[k++] = right[j++]; swaps += (left.length - i); }
        }
        while (i < left.length) arr[k++] = left[i++];
        while (j < right.length) arr[k++] = right[j++];
        return swaps;
    }
}
