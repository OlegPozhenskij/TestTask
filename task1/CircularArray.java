package task1;

import java.util.Arrays;

public class CircularArray {
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int m = Integer.parseInt(args[1]);

        int[] arr = new int[n];
        StringBuilder result = new StringBuilder();
        Arrays.setAll(arr, i -> ++i);

        int current = 0;
        do {
            result.append(arr[current]);
            current = (current + m - 1) % n;
        } while (current != 0);

        System.out.print(result);
    }
}
