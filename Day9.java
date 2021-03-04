import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day9 {
    public static void main(String[] args) {
        try {
            File answers = new File("Day9.txt");
            Scanner sc = new Scanner(answers);
            List<Long> answerList = new ArrayList<>();

            while (sc.hasNextLine())
                answerList.add(sc.nextLong());

            System.out.println(PartOne(answerList));
        }

        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static long PartOne(List<Long> numberList) {
        List<Long> possibleCombinations = new ArrayList<>();
        int currentMin = 0;
        int currentMax = 25;

        while (currentMax < numberList.size()) {
            for (int i = currentMin; i < currentMax; i++) {
                for (int j = currentMin; j < currentMax; j++) {
                    if (i != j)
                        possibleCombinations.add(numberList.get(i) + numberList.get(j));
                }
            }
            if (!possibleCombinations.contains(numberList.get(currentMax)))
                break;

            possibleCombinations.clear();
            currentMin++;
            currentMax++;
        }
        System.out.println(numberList.get(currentMax));
        return PartTwo(numberList, numberList.get(currentMax));
    }

    public static long PartTwo(List<Long> numberList, long invalidNum) {
        for (int i = 0; i < numberList.size(); i++) {
            long sum = numberList.get(i);
            long smallestNum = numberList.get(i);
            long biggestNum = numberList.get(i);

            for (int j = i + 1; sum <= invalidNum; j++) {
                sum += numberList.get(j);
                if (numberList.get(j) < smallestNum)
                    smallestNum = numberList.get(j);

                else if (numberList.get(j) > biggestNum)
                    biggestNum = numberList.get(j);

                if (sum == invalidNum)
                    return smallestNum + biggestNum;
            }
        }
        return 0;
    }
}
