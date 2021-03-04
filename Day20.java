import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day20 {
    public static void main(String[] args) {
        try {
            File passwords = new File("Day20.txt");
            Scanner sc = new Scanner(passwords);
            Map<Integer, String[]> tileList = new HashMap<>();
            int currentIndex = 0;
            int tileNum = 0;
            String[] toAdd = new String[10];

            while (sc.hasNextLine()) {
                String input = sc.nextLine();
                if (input.startsWith("Tile")) {
                    currentIndex = 0;
                    tileNum = Integer.parseInt(input.split("Tile ")[1].split(":")[0]);
                }
                else if (input.equals("")) {
                    tileList.put(tileNum, toAdd);
                    toAdd = new String[10];
                }
                else {
                    toAdd[currentIndex] = input;
                    currentIndex++;
                }
            }

            System.out.println(PartOne(tileList));
            //System.out.println(PartTwo(tileList));
        }

        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static long PartOne(Map<Integer, String[]> possibleTileList) {
        long result = 1;
        List<Integer> cornerTiles = new ArrayList<>();
        List<Integer> borderTiles = new ArrayList<>();
        List<Integer> insideTiles = new ArrayList<>();

        for (Integer key : possibleTileList.keySet()) {
            int numMatches = 0;
            String[] grid = possibleTileList.get(key);
            for (Integer keyComparison : possibleTileList.keySet()) {
                String[] comparisonGrid = possibleTileList.get(keyComparison);
                if (grid != comparisonGrid)
                    numMatches += Compare(grid, comparisonGrid, true);
            }
            if (numMatches == 2)
                cornerTiles.add(key);
            else if (numMatches == 3)
                borderTiles.add(key);
            else
                insideTiles.add(key);
        }

        for (Integer tileID : cornerTiles)
            result *= tileID;

        System.out.println(result);
        return PartTwo(possibleTileList, cornerTiles, borderTiles, insideTiles);
    }

    // Not right...
    public static int PartTwo(Map<Integer, String[]> possibleTileList, List<Integer> cornerTiles, List<Integer> borderTiles, List<Integer> insiderTiles) {
        int result = 0;
        int[][] tiles = new int[(int)Math.sqrt(possibleTileList.size())][(int)Math.sqrt(possibleTileList.size())];
        Map<Integer, String[]> newOrientations = new LinkedHashMap<>();
        for (Integer tile : cornerTiles) {
            int numMatches = 0;
            String[] grid = possibleTileList.get(tile);
            for (Integer comparisonTile : borderTiles) {
                String[] comparisonGrid = possibleTileList.get(comparisonTile);
                numMatches += Compare(grid, comparisonGrid, true);
                if (numMatches > 0) {
                    System.out.println("Tile " + tile + " matches " + comparisonTile);
                    break;
                }
            }
        }
        return result;
    }

    public static String[] RotateRight(String[] toRotate) {
        String[] rotatedGrid = new String[10];

        for (int i = 0; i < toRotate.length; i++) {
            StringBuilder toAdd = new StringBuilder();
            for (int j = 0; j < toRotate[i].length(); j++)
                toAdd.append(toRotate[toRotate.length - 1 - j].charAt(i));
            rotatedGrid[i] = toAdd.toString();
        }

        return rotatedGrid;
    }

    public static String[] Invert(String[] toInvert) {
        String[] invertedGrid = new String[10];

        for (int i = toInvert.length - 1; i >= 0; i--) {
            StringBuilder toAdd = new StringBuilder();
            for (int j = 0; j < toInvert[i].length(); j++)
                 toAdd.append(toInvert[i].charAt(toInvert.length - 1 - j));
            invertedGrid[i] = toAdd.toString();
        }

        return invertedGrid;
    }

    public static int Compare(String[] grid, String[] comparisonGrid, boolean original) {
        int matches = 0;

        if (original) {
            for (int i = 0; i < 8; i++) {
                grid = RotateRight(grid);
                matches += Compare(grid, comparisonGrid, false);
                if (matches > 0) {
                    matches = 1;
                    break;
                }
                if (i == 3)
                    grid = Invert(grid);
            }
        }

        else {
            for (int i = 0; i < 8; i++) {
                comparisonGrid = RotateRight(comparisonGrid);
                if (grid[0].equals(comparisonGrid[0]))
                    matches++;
                if (i == 3)
                    comparisonGrid = Invert(comparisonGrid);
            }
        }

        return matches;
    }

    public static String[] ArrangeTopBorder(Map<Integer, String[]> possibleTileList, List<Integer> cornerTiles, List<Integer> borderTiles) {
        Map<Integer, List<Integer>> invalidPairs = new HashMap<>();
        for (Integer tile : cornerTiles) {
            int numMatches = 0;
            String[] grid = possibleTileList.get(tile);
            for (Integer comparisonTile : borderTiles) {
                String[] comparisonGrid = possibleTileList.get(comparisonTile);
                numMatches += Compare(grid, comparisonGrid, true);
                if (numMatches > 0) {
                    System.out.println("Tile " + tile + " matches " + comparisonTile);
                    break;
                }
            }
        }
        return new String[5];
        //return comparisonGrid;
    }
}