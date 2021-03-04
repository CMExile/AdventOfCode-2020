import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Day17Test {
    public static void main(String[] args) {
        try {
            File configuration = new File("Day17.txt");
            Scanner sc = new Scanner(configuration);
            List<String> initialConfigurationList = new ArrayList<>();

            while (sc.hasNextLine())
                initialConfigurationList.add(sc.nextLine());

            System.out.println(PartOne(initialConfigurationList));
            System.out.println(PartTwo(initialConfigurationList));
        }

        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static int PartOne(List<String> initialConfigurationList) {
        int result = 0;
        int columns = initialConfigurationList.size();
        int cycles = 0;
        char[][][] gridList = new char[1][columns][columns];

        for (int i = 0; i < initialConfigurationList.size(); i++) {
            for (int j = 0; j < initialConfigurationList.get(i).length(); j++)
                gridList[0][i][j] = initialConfigurationList.get(i).charAt(j);
        }

        while (cycles < 6) {
            columns += 2;
            char[][][] tempList = new char[gridList.length + 2][columns][columns];

            for (char[][] twoDArray : tempList) {
                for (char[] charArray : twoDArray)
                    Arrays.fill(charArray, '.');
            }

            for (int i = 0; i < gridList.length; i++) {
                Arrays.fill(tempList[i + 1][0], '.');
                Arrays.fill(tempList[i + 1][columns - 1], '.');

                for (int j = 0; j < gridList[i].length; j++) {
                    tempList[i + 1][j + 1][0] = '.';
                    for (int k = 0; k < gridList[i][j].length; k++)
                        tempList[i + 1][j + 1][k + 1] = gridList[i][j][k];
                    tempList[i + 1][j + 1][tempList[i + 1][j + 1].length - 1] = '.';
                }
            }

            gridList = new char[gridList.length + 2][columns][columns];
            System.arraycopy(tempList, 0, gridList, 0, tempList.length);

            for (int i = 0; i < tempList.length; i++) {
                gridList[i] = Arrays.stream(tempList[i]).map(char[]::clone).toArray(char[][]::new);
                for (int j = 0; j < tempList[i].length; j++) {
                    for (int k = 0; k < tempList[i][j].length; k++) {
                        int count = CheckSurroundings3D(i, j, k, tempList);
                        if (tempList[i][j][k] == '#' && (count == 2 || count == 3))
                            gridList[i][j][k] = '#';

                        else if (tempList[i][j][k] == '#')
                            gridList[i][j][k] = '.';

                        else if (tempList[i][j][k] == '.' && count == 3)
                            gridList[i][j][k] = '#';

                        else
                            gridList[i][j][k] = '.';
                    }
                }
            }

            cycles++;
        }

        for (char[][] twoDArray : gridList) {
            for (char[] oneDArray : twoDArray) {
                for (char character : oneDArray) {
                    if (character == '#')
                        result++;
                }
            }
        }
        return result;
    }

    public static int PartTwo(List<String> initialConfigurationList) {
        int result = 0;
        int columns = initialConfigurationList.size();
        int cycles = 0;
        char[][][][] gridList = new char[1][1][columns][columns];

        for (int i = 0; i < initialConfigurationList.size(); i++) {
            for (int j = 0; j < initialConfigurationList.get(i).length(); j++)
                gridList[0][0][i][j] = initialConfigurationList.get(i).charAt(j);
        }

        while (cycles < 6) {
            columns += 2;
            char[][][][] tempList = new char[gridList.length + 2][gridList[0].length + 2][columns][columns];

            for (char[][][] threeDArray : tempList) {
                for (char[][] twoDArray : threeDArray) {
                    for (char[] charArray : twoDArray)
                        Arrays.fill(charArray, '.');
                }
            }

            for (int i = 0; i < gridList.length; i++) {
                for (int j = 0; j < gridList[i].length; j++) {
                    Arrays.fill(tempList[i + 1][j + 1][0], '.');
                    Arrays.fill(tempList[i + 1][j + 1][columns - 1], '.');

                    for (int k = 0; k < gridList[i][j].length; k++) {
                        tempList[i + 1][j + 1][k + 1][0] = '.';
                        for (int l = 0; l < gridList[i][j][k].length; l++)
                            tempList[i + 1][j + 1][k + 1][l + 1] = gridList[i][j][k][l];
                        tempList[i + 1][j + 1][k + 1][tempList[i + 1][j + 1][k + 1].length - 1] = '.';
                    }
                }
            }
            gridList = new char[gridList.length + 2][gridList[0].length + 2][columns][columns];
            System.arraycopy(tempList, 0, gridList, 0, tempList.length);

            for (int i = 0; i < tempList.length; i++) {
                for (int j = 0; j < tempList[i].length; j++) {
                    for (int k = 0; k < tempList[i][j].length; k++) {
                        for (int l = 0; l < tempList[i][j][k].length; l++) {
                            int count = CheckSurroundings4D(i, j, k, l, tempList);
                            if (tempList[i][j][k][l] == '#' && (count == 2 || count == 3))
                                gridList[i][j][k][l] = '#';

                            else if (tempList[i][j][k][l] == '#')
                                gridList[i][j][k][l] = '.';

                            else if (tempList[i][j][k][l] == '.' && count == 3)
                                gridList[i][j][k][l] = '#';

                            else
                                gridList[i][j][k][l] = '.';
                        }
                    }
                }
            }
            cycles++;
        }

        for (char[][][] threeDArray : gridList) {
            for (char[][] twoDArray : threeDArray) {
                for (char[] oneDArray : twoDArray) {
                    for (char character : oneDArray) {
                        if (character == '#')
                            result++;
                    }
                }
            }
        }

        return result;
    }

    private static int CheckSurroundings3D (int i, int j, int k, char[][][] tempList) {
        int count = 0;

        for (int threeD = i - 1; threeD <= i + 1; threeD++) {
            for (int twoD = j - 1; twoD <= j + 1; twoD++) {
                for (int oneD = k - 1; oneD <= k + 1; oneD++) {
                    if (!(threeD == i && twoD == j && oneD == k)) {
                        try {
                            if (tempList[threeD][twoD][oneD] == '#')
                                count++;
                        }
                        catch (IndexOutOfBoundsException e) {
                            continue;
                        }
                    }
                }
            }
        }

        return count;
    }

    // Not working... Answer is too high
    private static int CheckSurroundings4D (int i, int j, int k, int l, char[][][][] tempList) {
        int count = 0;

        for (int fourD = i - 1; fourD <= i + 1; fourD++) {
            for (int threeD = j - 1; threeD <= j + 1; threeD++) {
                for (int twoD = k - 1; twoD <= k + 1; twoD++) {
                    for (int oneD = l - 1; oneD <= l + 1; oneD++) {
                        if (!(fourD == i && threeD == j && twoD == k && oneD == l)) {
                            try {
                                if (tempList[fourD][threeD][twoD][oneD] == '#') {
                                    //System.out.println("character at " + fourD + ", " + threeD + ", " + twoD + ", " + oneD + " is " + tempList.get(fourD).get(threeD)[twoD][oneD]);
                                    count++;
                                }
                            }
                            catch (IndexOutOfBoundsException e) {
                                continue;
                            }
                        }
                    }
                }
            }
        }

        return count;
    }
}