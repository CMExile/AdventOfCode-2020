import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day4 {
    public static void main(String[] args) {
        try {
            File passports = new File("Day4.txt");
            Scanner sc = new Scanner(passports);
            List<String> passportList = new ArrayList<>();
            StringBuilder toAdd = new StringBuilder();

            while (sc.hasNextLine()) {
                String input = sc.nextLine();
                if (!input.equals(""))
                    toAdd.append(input).append(" ");

                else {
                    passportList.add(toAdd.toString());
                    toAdd.replace(0, toAdd.length(), "");
                }
            }

            if (!toAdd.toString().equals(""))
                passportList.add(toAdd.toString());

            System.out.println(PartOne(passportList));
            System.out.println(PartTwo(passportList));
        }

        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static int PartOne(List<String> passportList) {
        int result = 0;
        String patternString = "(byr:|iyr:|eyr:|hgt:|hcl:|ecl:|pid:)";
        Pattern pattern = Pattern.compile(patternString);

        for (String s : passportList) {
            Matcher matcher = pattern.matcher(s);
            int count = 0;

            while (matcher.find())
                count++;

            if (count == 7)
                result++;
        }

        return result;
    }

    public static int PartTwo(List<String> passportList) {
        int result = 0;
        String patternString = "(byr:((19[2-9][0-9])|(200[0-2]))|iyr:20(1[0-9]|20)|eyr:20(2[0-9]|30)|" +
                "hgt:(1([5-8][0-9]|9[0-3])cm|(59|6[0-9]|7[0-6])in)|hcl:#[0-9a-f]{6}|ecl:(amb|blu|brn|gry|grn|hzl|oth)|" +
                "pid:[0-9]{9})";
        Pattern pattern = Pattern.compile(patternString);

        for (String s : passportList) {
            Matcher matcher = pattern.matcher(s);
            int count = 0;

            while (matcher.find())
                count++;

            if (count == 7)
                result++;
        }

        return result;
    }
}
