import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day6 {
    public static void main(String[] args) {
        try {
            File answers = new File("Day6.txt");
            Scanner sc = new Scanner(answers);
            List<String> answerList = new ArrayList<>();

            while (sc.hasNextLine())
                answerList.add(sc.nextLine());

            System.out.println(PartOne(answerList));
            System.out.println(PartTwo(answerList));
        }

        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static int PartOne(List<String> answerList) {
        int result = 0;
        Set<Character> seenAnswers = new HashSet<>();

        for (String answer : answerList) {
            if (!answer.equals("")) {
                for (int j = 0; j < answer.length(); j++)
                    seenAnswers.add(answer.charAt(j));
            }
            else {
                result += seenAnswers.size();
                seenAnswers.clear();
            }
        }

        if (!answerList.get(answerList.size() - 1).equals(""))
            result += seenAnswers.size();

        return result;
    }

    private static int PartTwo(List<String> answerList) {
        int result = 0;
        int numPeople = 0;
        HashMap<Character, Integer> seenAnswers = new HashMap<>();
        for (String answer : answerList) {
            if (!answer.equals("")) {
                numPeople++;
                for (int j = 0; j < answer.length(); j++) {
                    if (seenAnswers.containsKey(answer.charAt(j)))
                        seenAnswers.put(answer.charAt(j), seenAnswers.get(answer.charAt(j)) + 1);

                    else
                        seenAnswers.put(answer.charAt(j), 1);
                }
            }

            else {
                for (Integer value : seenAnswers.values()) {
                    if (value == numPeople)
                        result++;
                }

                seenAnswers.clear();
                numPeople = 0;
            }
        }
        return result;
    }
}