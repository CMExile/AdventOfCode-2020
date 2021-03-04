import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class Day2 {
    public static void main(String[] args) {
        try {
            File passwords = new File("Day2.txt");
            Scanner sc = new Scanner(passwords);
            List<String> passwordList = new ArrayList<>();
            while (sc.hasNextLine())
                passwordList.add(sc.nextLine());

            System.out.println(PartOne(passwordList));
            System.out.println(PartTwo(passwordList));
        }

        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static int PartOne(List<String> passwordList) {
        int invalidPasswords = 0;
        int minNumRequired;
        int maxNumRequired;
        char toMatch;

        for (String currentPass : passwordList) {
            int passStartIndex = currentPass.indexOf(':') + 2;
            int matches = 0;
            toMatch = currentPass.charAt(passStartIndex - 3);
            minNumRequired = (currentPass.charAt(1) != '-') ? Integer.parseInt(currentPass.substring(0, 2)) :
                    Integer.parseInt("" + currentPass.charAt(0));

            if (currentPass.charAt(currentPass.indexOf('-') + 2) != ' ')
                maxNumRequired = Integer.parseInt(currentPass.substring(currentPass.indexOf('-') + 1,
                        currentPass.indexOf('-') + 3));

            else
                maxNumRequired = Integer.parseInt("" + currentPass.charAt(currentPass.indexOf('-') + 1));

            for (int i = passStartIndex; i < currentPass.length(); i++) {
                if (currentPass.charAt(i) == toMatch)
                    matches++;
            }

            if (!(matches >= minNumRequired && matches <= maxNumRequired))
                invalidPasswords++;
        }

        return (passwordList.size() - invalidPasswords);
    }

    public static int PartTwo(List<String> passwordList) {
        int invalidPasswords = 0;
        int firstIndex;
        int lastIndex;
        char toMatch;

        for (String currentPass : passwordList) {
            int passStartIndex = currentPass.indexOf(':') + 2;
            toMatch = currentPass.charAt(passStartIndex - 3);
            firstIndex = (currentPass.charAt(1) != '-') ? Integer.parseInt(currentPass.substring(0, 2)) :
                    Integer.parseInt("" + currentPass.charAt(0));

            if (currentPass.charAt(currentPass.indexOf('-') + 2) != ' ')
                lastIndex = Integer.parseInt(currentPass.substring(currentPass.indexOf('-') + 1,
                        currentPass.indexOf('-') + 3));

            else
                lastIndex = Integer.parseInt("" + currentPass.charAt(currentPass.indexOf('-') + 1));

            if (currentPass.charAt(passStartIndex + firstIndex - 1) == toMatch && currentPass.charAt(passStartIndex + lastIndex - 1) == toMatch)
                invalidPasswords++;

            else if (currentPass.charAt(passStartIndex + firstIndex - 1) != toMatch && currentPass.charAt(passStartIndex + lastIndex - 1) != toMatch)
                invalidPasswords++;
        }

        return (passwordList.size() - invalidPasswords);
    }
}
