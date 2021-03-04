import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day10 {
    public static void main(String[] args) {
        try {
            File adapters = new File("Day10.txt");
            Scanner sc = new Scanner(adapters);
            List<Integer> adapterList = new ArrayList<>();

            while (sc.hasNextLine())
                adapterList.add(sc.nextInt());

            Collections.sort(adapterList);
            adapterList.add(0, 0);
            adapterList.add(adapterList.get(adapterList.size() - 1) + 3);

            List<Integer> sequentialNums = new ArrayList<>();
            List<List<Integer>> listOfSequentialNums = new ArrayList<>();
            for (int i = 1; i < adapterList.size() - 1; i++) {
                if (adapterList.get(i + 1) - adapterList.get(i) != 3 && adapterList.get(i) - adapterList.get(i - 1) != 3)
                    sequentialNums.add(adapterList.get(i));

                else if (sequentialNums.size() != 0) {
                    listOfSequentialNums.add(new ArrayList<>(sequentialNums));
                    sequentialNums.clear();
                }
            }

            System.out.println(PartOne(adapterList));
            System.out.println(PartTwo(listOfSequentialNums));
        }

        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static int PartOne(List<Integer> adapterList) {
        int lastSeen = 0;
        int oneCounter = 0;
        int threeCounter = 0;

        for (int adapter : adapterList) {
            if (adapter - lastSeen == 1)
                oneCounter++;

            else if (adapter - lastSeen == 3)
                threeCounter++;

            lastSeen = adapter;
        }

        return oneCounter * threeCounter;
    }

    public static long PartTwo(List<List<Integer>> adapterList) {
        long result = 1;

        for (List<Integer> integers : adapterList) {
            if (integers.size() == 3)
                result *= 7;

            else if (integers.size() == 2)
                result *= 4;

            else if (integers.size() == 1)
                result *= 2;

            else
                System.out.println(integers.size());
        }

        return result;
    }
}
