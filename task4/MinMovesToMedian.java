package task4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class MinMovesToMedian {
    public static void main(String[] args) {

        String fileName = args[0];

        //получили nums
        int[] nums = readNumbersFromFile(fileName);

        int minMoves = findMinMoves(nums);

        System.out.print(minMoves);
    }

    private static int[] readNumbersFromFile(String fileName) {
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            int count = 0;
            while (scanner.hasNextInt()) {
                count++;
                scanner.nextInt();
            }
            scanner.close();


            int[] nums = new int[count];
            scanner = new Scanner(file);
            for (int i = 0; i < count; i++) {
                nums[i] = scanner.nextInt();
            }
            scanner.close();

            return nums;
        } catch (FileNotFoundException e) {
            System.out.println("File not found : " + fileName);
            return new int[0];
        }
    }

    private static int findMinMoves(int[] nums) {

        // Найдем медианное значение элементов массива
        Arrays.sort(nums);
        int median = nums[nums.length / 2];

        // Посчитаем количество ходов для каждого элемента
        int moves = 0;
        for (int num : nums) {
            moves += Math.abs(num - median);
        }

        return moves;

    }
}
