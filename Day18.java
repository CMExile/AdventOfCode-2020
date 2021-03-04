import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day18 {
    public static void main(String[] args) {
        try {
            File problems = new File("Day18.txt");
            Scanner sc = new Scanner(problems);
            List<StringBuilder> problemList = new ArrayList<>();

            while (sc.hasNextLine())
                problemList.add(new StringBuilder(sc.nextLine()));

            System.out.println(Solve(problemList, false));
            System.out.println(Solve(problemList, true));
        }

        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static long Solve(List<StringBuilder> problemList, boolean AdditionPrecedence) {
        long result = 0;

        for (StringBuilder problem : problemList) {
            while (problem.toString().contains("(")) {
                StringBuilder newLine = new StringBuilder();
                String[] problemsInParentheses = problem.toString().split("(?=\\(.*\\))");
                for (String bracket : problemsInParentheses) {
                    if (bracket.contains("(") && bracket.contains(")"))
                        bracket = bracket.replaceFirst(Pattern.quote(bracket.split("(?=\\()")[0].split("(?<=\\))")[0]), SolveParentheses(bracket, AdditionPrecedence));

                    newLine.append(bracket);
                }
                problem = newLine;
            }
            result += Long.parseLong(SolveSum(problem.toString(), true, AdditionPrecedence));
        }
        return result;
    }

    private static String SolveParentheses(String problemString, boolean AdditionPrecedence) {
        boolean noAdditions = false;
        while (problemString.split("\\)")[0].split(" ").length > 1) {
            if (problemString.contains("+") && AdditionPrecedence && !noAdditions) {
                String patternString = "\\d* \\+ \\d*";
                Pattern pattern = Pattern.compile(patternString);
                Matcher matcher = pattern.matcher(problemString);
                matcher.find();
                String currentSum = matcher.group(0);
                if (currentSum.startsWith(" + ") || currentSum.endsWith(" + ")) {
                    noAdditions = true;
                    continue;
                }

                problemString = problemString.replaceFirst(Pattern.quote(currentSum), SolveSum(currentSum, false, true));
            }

            else {
                String[] currentSumNums = problemString.split("\\(")[1].split(" ");
                String currentSum = (currentSumNums[0] + " " + currentSumNums[1] + " " + currentSumNums[2]).split("\\)")[0];
                problemString = problemString.replaceFirst(Pattern.quote(currentSum), SolveSum(currentSum, false, false));
            }
        }
        return problemString.split("\\(")[1].split("\\)")[0];
    }

    private static String SolveSum(String currentSum, boolean lastStep, boolean AdditionPrecedence) {
        if (!lastStep) {
            long num1 = Long.parseLong(currentSum.split(" ")[0]);
            long num2 = Long.parseLong(currentSum.split(" ")[2]);
            char operator = currentSum.split(" ")[1].charAt(0);
            long result = (operator == '+') ? num1 + num2 : num1 * num2;
            return String.valueOf(result);
        }

        long result;
        String toReplace;
        while (currentSum.split(" ").length > 1) {
            if (currentSum.contains("+") && AdditionPrecedence) {
                String patternString = "\\d* \\+ \\d*";
                Pattern pattern = Pattern.compile(patternString);
                Matcher matcher = pattern.matcher(currentSum);
                matcher.find();
                toReplace = matcher.group(0);
                result = Long.parseLong(toReplace.split(" \\+ ")[0]) + Long.parseLong(toReplace.split(" \\+ ")[1]);
            }

            else {
                long num1 = Long.parseLong(currentSum.split(" ")[0]);
                long num2 = Long.parseLong(currentSum.split(" ")[2]);
                char operator = currentSum.split(" ")[1].charAt(0);
                result = (operator == '+') ? num1 + num2 : num1 * num2;
                toReplace = currentSum.split(" ")[0] + " " + currentSum.split(" ")[1] + " " + currentSum.split(" ")[2];
            }
            currentSum = currentSum.replaceFirst(Pattern.quote(toReplace), String.valueOf(result));
        }
        return currentSum;
    }
}