import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class Day1 {
    public static void main(String[] args) {
        try {
            File expenses = new File("Day1.txt");
            Scanner sc = new Scanner(expenses);
            List<Integer> expenseList = new ArrayList<>();
            while (sc.hasNextLine())
                expenseList.add(Integer.parseInt(sc.nextLine()));

            PartOne(expenseList);
            PartTwo(expenseList);
        }

        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    // Looping through the input until two inputs add to 2020, then printing the product
    public static void PartOne(List<Integer> expenseList) {
        for (int i = 0; i < expenseList.size(); i++) {
            for (int j = i + 1; j < expenseList.size(); j++) {
                if (expenseList.get(i) + expenseList.get(j) == 2020) {
                    System.out.println(expenseList.get(i) * expenseList.get(j));
                    return;
                }
            }
        }
    }

    // Looping through the input until three inputs add to 2020, then printing the product
    public static void PartTwo(List <Integer> expenseList) {
        for (int i = 0; i < expenseList.size() / 2; i++) {
            for (int j = expenseList.size() - 1; j > expenseList.size() / 2; j--) {
                for (int k = i + 1; k < expenseList.size(); k++) {
                    if (expenseList.get(i) + expenseList.get(j) + expenseList.get(k) == 2020 && k != j) {
                        System.out.println(expenseList.get(i) * expenseList.get(j) * expenseList.get(k));
                        return;
                    }
                }
            }
        }
    }
}