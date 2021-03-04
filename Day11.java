import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day11 {
    public static void main(String[] args) {
        try {
            File seats = new File("Day11.txt");
            Scanner sc = new Scanner(seats);
            List<StringBuilder> seatList = new ArrayList<>();

            while (sc.hasNextLine()) {
                String input = sc.nextLine();
                input = input.replaceAll("L", "#");
                seatList.add(new StringBuilder(input));
            }

            List<StringBuilder> seatListCopy = new ArrayList<>();
            for (StringBuilder seat : seatList)
                seatListCopy.add(new StringBuilder(seat));

            System.out.println(PartOne(seatList));
            System.out.println(PartTwo(seatListCopy));
        }

        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static int PartOne(List<StringBuilder> seatList) {
        int result = 0;
        int changeCount;

        do {
            List<StringBuilder> seatListCopy = new ArrayList<>();
            for (StringBuilder seat : seatList)
                seatListCopy.add(new StringBuilder(seat));

            changeCount = 0;

            for (int i = 0; i < seatListCopy.size(); i++) {
                for (int j = 0; j < seatListCopy.get(i).length(); j++)
                    changeCount += countSurroundingsOne(seatList, seatListCopy, i, j);
            }
        } while (changeCount != 0);

        for (StringBuilder seatLine : seatList) {
            for (int j = 0; j < seatLine.length(); j++) {
                if (seatLine.charAt(j) == '#')
                    result++;
            }
        }

        return result;
    }

    public static int PartTwo(List<StringBuilder> seatList) {
        int result = 0;
        int changeCount;

        do {
            List<StringBuilder> seatListCopy = new ArrayList<>();
            for (StringBuilder seat : seatList)
                seatListCopy.add(new StringBuilder(seat));

            changeCount = 0;

            for (int i = 0; i < seatListCopy.size(); i++) {
                for (int j = 0; j < seatListCopy.get(i).length(); j++)
                    changeCount += countSurroundingsTwo(seatList, seatListCopy, i, j);
            }
        } while (changeCount != 0);

        for (StringBuilder seatLine : seatList) {
            for (int j = 0; j < seatLine.length(); j++) {
                if (seatLine.charAt(j) == '#')
                    result++;
            }
        }

        return result;
    }

    public static int countSurroundingsOne(List<StringBuilder> seatList, List<StringBuilder> seatListCopy, int i, int j) {
        int count = 0;
        int changeCount = 0;

        for (int yAxis = i - 1; yAxis < i + 2; yAxis++) {
            for (int xAxis = j - 1; xAxis < j + 2; xAxis++) {
                if (!(yAxis == i && xAxis == j)) {
                    try {
                        count = (seatListCopy.get(yAxis).charAt(xAxis) == '#') ? count + 1 : count;
                    } catch (IndexOutOfBoundsException e) {
                        continue;
                    }
                }
            }
        }
        if (count >= 4 && seatList.get(i).charAt(j) == '#') {
            seatList.get(i).replace(j, j + 1, "" + 'L');
            changeCount++;
        }

        else if (count == 0 && seatList.get(i).charAt(j) == 'L') {
            seatList.get(i).replace(j, j + 1, "" + '#');
            changeCount++;
        }

        return changeCount;
    }

    public static int countSurroundingsTwo(List<StringBuilder> seatList, List<StringBuilder> seatListCopy, int i, int j) {
        int count = 0;
        int changeCount = 0;
        int range = 1;
        boolean topLeft, top, topRight, left, right, bottomLeft, bottom, bottomRight;
        topLeft = top = topRight = left = right = bottomLeft = bottom = bottomRight = false;

        // Had a challenge with my friend to see how verbose I could make this function... Please don't judge!

        while (!topLeft || !top || !topRight || !left || !right || !bottomLeft || !bottom || !bottomRight) {
            if (!topLeft && (i - range < 0 || j - range < 0))
                topLeft = true;

            else if (!topLeft && seatListCopy.get(i - range).charAt(j - range) != '.') {
                count = (seatListCopy.get(i - range).charAt(j - range) == '#') ? count + 1 : count;
                topLeft = true;
            }

            if (!top && i - range < 0)
                top = true;

            else if (!top && seatListCopy.get(i - range).charAt(j) != '.') {
                count = (seatListCopy.get(i - range).charAt(j) == '#') ? count + 1 : count;
                top = true;
            }

            if (!topRight && (i - range < 0 || j + range > seatList.get(i - range).length() - 1))
                topRight = true;

            else if (!topRight && seatListCopy.get(i - range).charAt(j + range) != '.') {
                count = (seatListCopy.get(i - range).charAt(j + range) == '#') ? count + 1 : count;
                topRight = true;
            }

            if (!left && j - range < 0)
                left = true;

            else if (!left && seatListCopy.get(i).charAt(j - range) != '.') {
                count = (seatListCopy.get(i).charAt(j - range) == '#') ? count + 1 : count;
                left = true;
            }

            if (!right && j + range > seatList.get(i).length() - 1)
                right = true;

            else if (!right && seatListCopy.get(i).charAt(j + range) != '.') {
                count = (seatListCopy.get(i).charAt(j + range) == '#') ? count + 1 : count;
                right = true;
            }

            if (!bottomLeft && (i + range > seatList.size() - 1 || j - range < 0))
                bottomLeft = true;

            else if (!bottomLeft && seatListCopy.get(i + range).charAt(j - range) != '.') {
                count = (seatListCopy.get(i + range).charAt(j - range) == '#') ? count + 1 : count;
                bottomLeft = true;
            }

            if (!bottom && i + range > seatList.size() - 1)
                bottom = true;

            else if (!bottom && seatListCopy.get(i + range).charAt(j) != '.') {
                count = (seatListCopy.get(i + range).charAt(j) == '#') ? count + 1 : count;
                bottom = true;
            }

            if (!bottomRight && (i + range > seatList.size() - 1 || j + range > seatList.get(i + range).length() - 1))
                bottomRight = true;

            else if (!bottomRight && seatListCopy.get(i + range).charAt(j + range) != '.') {
                count = (seatListCopy.get(i + range).charAt(j + range) == '#') ? count + 1 : count;
                bottomRight = true;
            }

            range++;
        }

        if (count >= 5 && seatList.get(i).charAt(j) == '#') {
            seatList.get(i).replace(j, j + 1, "" + 'L');
            changeCount++;
        }

        else if (count == 0 && seatList.get(i).charAt(j) == 'L') {
            seatList.get(i).replace(j, j + 1, "" + '#');
            changeCount++;
        }

        return changeCount;
    }
}