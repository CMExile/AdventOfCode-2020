import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Day5 {
    public static void main(String[] args) {
        try {
            File instructions = new File("Day5.txt");
            Scanner sc = new Scanner(instructions);
            List<String> boardingPassList = new ArrayList<>();

            while (sc.hasNextLine())
                boardingPassList.add(sc.nextLine());

            System.out.println(PartOne(boardingPassList));
            System.out.println(PartTwo(boardingPassList));
        }

        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static int PartOne(List<String> boardingPassList) {
        int result = 0;

        for (String boardingPass : boardingPassList) {
            int lowerRange = 0;
            int upperRange = 127;
            int lowerColumnRange = 0;
            int upperColumnRange = 7;

            for (int j = 0; j < boardingPass.length(); j++) {
                if (boardingPass.charAt(j) == 'F')
                    upperRange -= ((upperRange - lowerRange + 1) / 2);

                else if (boardingPass.charAt(j) == 'B')
                    lowerRange += ((upperRange - lowerRange + 1) / 2);

                else if (boardingPass.charAt(j) == 'R')
                    lowerColumnRange += ((upperColumnRange - lowerColumnRange + 1) / 2);

                else
                    upperColumnRange -= ((upperColumnRange - lowerColumnRange + 1) / 2);
            }

            if ((lowerRange * 8) + lowerColumnRange > result)
                result = ((lowerRange * 8) + lowerColumnRange);
        }
        return result;
    }

    public static int PartTwo(List<String> boardingPassList) {
        List<Integer> seenSeats = new ArrayList<>();

        for (String boardingPass : boardingPassList) {
            int lowerRange = 0;
            int upperRange = 127;
            int lowerColumnRange = 0;
            int upperColumnRange = 7;

            for (int j = 0; j < boardingPass.length(); j++) {
                if (boardingPass.charAt(j) == 'F')
                    upperRange -= ((upperRange - lowerRange + 1) / 2);

                else if (boardingPass.charAt(j) == 'B')
                    lowerRange += ((upperRange - lowerRange + 1) / 2);

                else if (boardingPass.charAt(j) == 'R')
                    lowerColumnRange += ((upperColumnRange - lowerColumnRange + 1) / 2);

                else
                    upperColumnRange -= ((upperColumnRange - lowerColumnRange + 1) / 2);
            }

            if (lowerRange != 0 && lowerRange != 127)
                seenSeats.add((lowerRange * 8) + lowerColumnRange);
        }

        Collections.sort(seenSeats);

        for (int i = 1; i < seenSeats.size(); i++) {
            if (seenSeats.get(i) - seenSeats.get(i - 1) == 2)
                return seenSeats.get(i) - 1;
        }

        return 0;
    }
}