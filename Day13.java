import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day13 {
    public static void main(String[] args) {
        try {
            File busses = new File("Day13.txt");
            Scanner sc = new Scanner(busses);

            int timeToWait = Integer.parseInt(sc.nextLine());
            String[] splitBusList = sc.nextLine().split("(x,|,)");
            Map<Integer, Integer> busListTwo = new LinkedHashMap<>();

            for (int i = 0; i < splitBusList.length; i++) {
                if (!splitBusList[i].equals(""))
                    busListTwo.put(Integer.parseInt(splitBusList[i]), i);
            }

            System.out.println(PartOne(timeToWait, busListTwo.keySet()));
            System.out.println(PartTwo(busListTwo));
        }

        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static int PartOne(int timeToWait, Set<Integer> busList) {
        SortedMap<Integer, Integer> timeWaiting = new TreeMap<>();

        for (Integer bus : busList)
            timeWaiting.put((timeToWait - (timeToWait % bus) + bus) - timeToWait, bus);

        return timeWaiting.firstKey() * timeWaiting.get(timeWaiting.firstKey());
    }

    // Not right... Issue is with multiplication
    public static long PartTwo(Map<Integer, Integer> busList) {
        long multiplier = 1;
        long toMultiply = busList.keySet().iterator().next();
        List<Integer> keys = new ArrayList<>(busList.keySet());
        List<Integer> values = new ArrayList<>(busList.values());
        long lastSeen = 0;
        keys.remove(0);
        values.remove(0);

        while (keys.size() > 0) {
            long toCompare = keys.get(0);
            System.out.println("lastSeen: " + lastSeen + ", toMultiply: " + toMultiply + ", key: " + toCompare);
            while (toCompare - ((lastSeen + (toMultiply * multiplier)) % toCompare) != values.get(0))
                multiplier++;

            lastSeen += multiplier * toMultiply;

            long biggestNum = (toMultiply > keys.get(0)) ? toMultiply : keys.get(0);
            long smallestNum = (biggestNum == toMultiply) ? keys.get(0) : toMultiply;
            multiplier = 1;

            while (biggestNum * multiplier % smallestNum != 0)
                multiplier++;

            toMultiply = biggestNum * multiplier;
            multiplier = 1;

            keys.remove(0);
            values.remove(0);

        }

        return lastSeen;
    }
}
