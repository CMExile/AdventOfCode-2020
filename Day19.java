import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day19 {
    public static void main(String[] args) {
        try {
            File passwords = new File("Day19.txt");
            Scanner sc = new Scanner(passwords);
            Map<Integer, String> ruleList = new TreeMap<>();
            List<String> messageList = new ArrayList<>();
            String input;
            int longestWord = 0;

            while (sc.hasNextLine()) {
                input = sc.nextLine();
                if (!input.equals(""))
                    ruleList.put(Integer.parseInt(input.split(": ")[0]), input.split(": ")[1]);
                else
                    break;
            }

            while (sc.hasNextLine()) {
                input = sc.nextLine();
                if (input.length() > longestWord)
                    longestWord = input.length();
                messageList.add(input);
            }

            System.out.println(Solve(ruleList, messageList, 1, longestWord));

            ruleList.put(0, "8 11");
            ruleList.put(8, "42+");
            ruleList.put(11, "42 31 | 42 11 31");

            System.out.println(Solve(ruleList, messageList, 2, longestWord));
        }

        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static int Solve(Map<Integer, String> ruleList, List<String> messageList, int part, int longestWord) {
        int result = 0;

        if (part == 2) {
            String originalString = ruleList.get(11);
            String[] numsInOriginal = ruleList.get(11).split(" ");
            int numRepeats = 2;
            ruleList.put(11, originalString + " |" + (" " + numsInOriginal[0]).repeat(numRepeats) + (" " + numsInOriginal[1]).repeat(numRepeats));

            for (int i = 0; i < 2; i++) {
                numRepeats++;
                String toReplace = ruleList.get(11);
                ruleList.put(11, toReplace + " | " + (" " + numsInOriginal[0]).repeat(numRepeats) + (" " + numsInOriginal[1]).repeat(numRepeats));
            }
            ruleList.put(11, ruleList.get(11).replace(" 11", ""));
        }

        while (ruleList.get(0).matches(".*[0-9].*")) {
            String patternString = "\\d+";
            Pattern pattern = Pattern.compile(patternString);
            Matcher matcher = pattern.matcher(ruleList.get(0));
            matcher.find();
            String toReplace = matcher.group(0);
            String replaceWith = "(" + ruleList.get(Integer.parseInt(toReplace)).replaceAll("\"", "") + ")";
            ruleList.put(0, ruleList.get(0).replaceFirst(Pattern.quote(toReplace), replaceWith));
        }

        ruleList.put(0, ruleList.get(0).replaceAll(" ", ""));
        ruleList.put(0, ruleList.get(0).replaceAll("\\(a\\)", "a"));
        ruleList.put(0, ruleList.get(0).replaceAll("\\(b\\)", "b"));
        Pattern pattern = Pattern.compile(ruleList.get(0));

        for (String message : messageList) {
            Matcher matcher = pattern.matcher(message);
            if (matcher.matches())
                result++;
        }

        return result;
    }
}