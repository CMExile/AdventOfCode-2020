import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day3 {
    public static void main(String[] args) {
        try {
            File map = new File("Day3.txt");
            Scanner sc = new Scanner(map);
            List<String> mapList = new ArrayList<>();
            int[][] movements = {{1, 1}, {3, 1}, {5, 1}, {7, 1}, {1, 2}};
            while (sc.hasNextLine())
                mapList.add(sc.nextLine());

            for (int i = 0; i < mapList.size(); i++) {
                StringBuilder toAdd = new StringBuilder(mapList.get(i));

                while (toAdd.length() < mapList.size() * 7)
                    toAdd.append(mapList.get(i));

                mapList.set(i, toAdd.toString());
            }

            System.out.println(PartOne(mapList));
            System.out.println(PartTwo(mapList, movements));
        }

        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static int PartOne(List<String> mapList) {
        int numOfTrees = 0;
        int currentPosition = 0;

        for (int i = 1; i < mapList.size(); i++) {
            currentPosition += 3;

            if (mapList.get(i).charAt(currentPosition) == '#')
                numOfTrees++;
        }

        return numOfTrees;
    }

    public static long PartTwo(List<String> mapList, int[][] movements) {
        long result = 0;

        for (int[] movement : movements) {
            int numOfTrees = 0;
            int currentPosition = 0;

            for (int j = movement[1]; j < mapList.size(); j += movement[1]) {
                currentPosition += movement[0];

                if (mapList.get(j).charAt(currentPosition) == '#')
                    numOfTrees++;
            }

            if (result != 0)
                result *= numOfTrees;

            else
                result = numOfTrees;
        }
        return result;
    }
}
