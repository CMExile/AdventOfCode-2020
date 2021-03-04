import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day7 {
    public static void main(String[] args) {
        try {
            File bags = new File("Day7.txt");
            Scanner sc = new Scanner(bags);
            List<String> bagList = new ArrayList<>();

            while (sc.hasNextLine())
                    bagList.add(sc.nextLine());

            System.out.println(PartOne(bagList));
            System.out.println(PartTwo(bagList));
        }

        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static int PartOne(List<String> bagList) {
        List<String> bagsToSearch = new ArrayList<>();
        List<String> nextSearch = new ArrayList<>();
        List<String> seenBags = new ArrayList<>();
        bagsToSearch.add("shiny gold bag");

        while (bagsToSearch.size() > 0) {
            for (String toSearch : bagsToSearch) {
                for (String bag : bagList) {
                    if (bag.split(" contain ")[1].contains(toSearch) && !seenBags.contains(bag.split("s contain ")[0])) {
                        nextSearch.add(bag.split("s contain ")[0]);
                        seenBags.add(bag.split("s contain ")[0]);
                    }
                }
            }
            bagsToSearch.clear();
            bagsToSearch.addAll(nextSearch);
            nextSearch.clear();
        }

        return seenBags.size();
    }


    public static int PartTwo(List<String> bagList) {
        int result = 0;
        List<String> bagsToSearch = new ArrayList<>();
        List<String> nextSearch = new ArrayList<>();
        bagsToSearch.add("1 shiny gold bag");
        while (bagsToSearch.size() > 0) {
            for (String toSearch : bagsToSearch) {
                for (String bag : bagList) {
                    if (bag.split("s contain ")[0].equals(toSearch.split("\\d ")[1])) {
                        String[] bags = bag.split("(s contain |, |\\.)");

                        for (int i = 1; i < bags.length; i++) {
                            if (!bags[1].contains("other")) {
                                int nextBagAmount = Integer.parseInt(toSearch.split(" ")[0]) * Integer.parseInt(bags[i].split("\\s")[0]);
                                result += nextBagAmount;
                                nextSearch.add(nextBagAmount + " " + bags[i].split("\\d ")[1].split(" bag")[0] + " bag");
                            }
                        }
                    }
                }
            }
            bagsToSearch.clear();
            bagsToSearch.addAll(nextSearch);
            nextSearch.clear();
        }

        return result;
    }
}