import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Day15 {
    public static void main(String[] args) {
        try {
            File numbers = new File("Day15.txt");
            Scanner sc = new Scanner(numbers);
            String[] numberList = sc.nextLine().split(",");

            System.out.println(NumberSpokenArrays(numberList, 2020));
            System.out.println(NumberSpokenArrays(numberList, 30000000));
        }

        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    // Method #1 of doing problem, part 2 far slower
    private static int NumberSpokenMap(String[] numberList, int iterations) {
        Map<Integer, Integer> numberLastSeenAt = new LinkedHashMap<>(iterations / 2);
        int currentNumber = 1;
        int nextNumber = 0;
        int toCheck;
        for (String s : numberList) {
            numberLastSeenAt.put(Integer.parseInt(s), currentNumber);
            currentNumber++;
        }

        while (currentNumber < iterations) {
            toCheck = (numberLastSeenAt.containsKey(nextNumber)) ? currentNumber - numberLastSeenAt.get(nextNumber) : 0;
            numberLastSeenAt.put(nextNumber, currentNumber);
            numberLastSeenAt.put(nextNumber, currentNumber);
            nextNumber = toCheck;
            currentNumber++;
        }

        return nextNumber;
    }

    // Method #2 of doing problem, part 2 far faster
    private static int NumberSpokenArrays(String[] numberList, int iterations) {
        int[] numberLastSeenAt = new int[iterations];
        boolean[] numberEverSeen = new boolean[iterations];
        int currentNumber = 1;
        int nextNumber = 0;
        int toCheck;

        for (String s : numberList) {
            numberLastSeenAt[Integer.parseInt(s)] = currentNumber;
            numberEverSeen[Integer.parseInt(s)] = true;
            currentNumber++;
        }

        while (currentNumber < iterations) {
            toCheck = (numberEverSeen[nextNumber]) ? currentNumber - numberLastSeenAt[nextNumber] : 0;
            numberLastSeenAt[nextNumber] = currentNumber;
            numberEverSeen[nextNumber] = true;
            nextNumber = toCheck;
            currentNumber++;
        }

        return nextNumber;
    }
}