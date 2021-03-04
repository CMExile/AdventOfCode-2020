import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day16 {
    public static void main(String[] args) {
        try {
            File tickets = new File("Day16.txt");
            Scanner sc = new Scanner(tickets);
            List<String> validRangeList = new ArrayList<>();
            List<String> nearbyTicketList = new ArrayList<>();

            while (sc.hasNextLine()) {
                String input = sc.nextLine();
                if (!input.equals(""))
                    validRangeList.add(input);

                else
                    break;
            }
            sc.nextLine();
            String myTicket = sc.nextLine();
            sc.nextLine();
            sc.nextLine();
            while (sc.hasNextLine())
                nearbyTicketList.add(sc.nextLine());

            System.out.println(PartOne(validRangeList, myTicket, nearbyTicketList));
        }

        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static long PartOne(List<String> validRangeList, String myTicket, List<String> nearbyTicketList) {
        int result = 0;
        SortedSet<Integer> validNumbers = new TreeSet<>();
        List<String> validTickets = new ArrayList<>();

        for (String line : validRangeList) {
            String[] ranges = line.split(": ")[1].split(" or ");
            for (String range : ranges) {
                for (int i = Integer.parseInt(range.split("-")[0]); i <= Integer.parseInt(range.split("-")[1]); i++)
                    validNumbers.add(i);
            }
        }

        for (String ticket : nearbyTicketList){
            String[] numsToSearch = ticket.split(",");
            boolean valid = true;
            for (String toSearch : numsToSearch) {
                if (!validNumbers.contains(Integer.parseInt(toSearch))) {
                    valid = false;
                    result += Integer.parseInt(toSearch);
                }
            }
            if (valid)
                validTickets.add(ticket);
        }

        System.out.println(result);

        return PartTwo(validRangeList, myTicket, validTickets);
    }

    private static long PartTwo(List<String> validRangeList, String myTicket, List<String> nearbyTicketList) {
        long result = 1;
        List<String[]> ranges = new ArrayList<>();
        for (String rangeString : validRangeList)
            ranges.add(rangeString.split(": ")[1].split(" or "));

        boolean[][] nearbyTicketsValid = new boolean[validRangeList.size()][validRangeList.size()];
        for (boolean[] booleans : nearbyTicketsValid) Arrays.fill(booleans, true);
        for (String s : nearbyTicketList) {
            String[] numsToCheck = s.split(",");
            for (int j = 0; j < numsToCheck.length; j++) {
                for (int k = 0; k < ranges.size(); k++) {
                    int numToCheck = Integer.parseInt(numsToCheck[j]);
                    int rangeOne0 = Integer.parseInt(ranges.get(k)[0].split("-")[0]);
                    int rangeOne1 = Integer.parseInt(ranges.get(k)[0].split("-")[1]);
                    int rangeTwo0 = Integer.parseInt(ranges.get(k)[1].split("-")[0]);
                    int rangeTwo1 = Integer.parseInt(ranges.get(k)[1].split("-")[1]);
                    if (!(numToCheck >= rangeOne0 && numToCheck <= rangeOne1) && !(numToCheck >= rangeTwo0 && numToCheck <= rangeTwo1))
                        nearbyTicketsValid[j][k] = false;
                }
            }
        }

        SortedMap<Integer, Integer> orderOfColumns = new TreeMap<>();
        for (int i = 0; i < nearbyTicketsValid.length; i++) {
            int count = 0;
            for (int j = 0; j < nearbyTicketsValid[i].length; j++) {
                if (nearbyTicketsValid[i][j])
                    count++;
            }
            orderOfColumns.put(count, i);
        }

        SortedMap<Integer, Integer> solvedFields = solveFields(nearbyTicketsValid, orderOfColumns);

        for (int i = 0; i < 20; i++) {
            String[] myTicketValues = myTicket.split(",");
            if (validRangeList.get(solvedFields.get(i)).startsWith("departure"))
                result *= Long.parseLong(myTicketValues[i]);
        }
        return result;
    }

    private static SortedMap<Integer, Integer> solveFields(boolean[][] validPairs, SortedMap<Integer, Integer> orderOfColumns) {
        SortedMap<Integer, Integer> solvedFields = new TreeMap<>();
        for (int i = orderOfColumns.firstKey(); i <= orderOfColumns.lastKey(); i++) {
            for (int j = 0; j < validPairs[orderOfColumns.get(i)].length; j++) {
                if (validPairs[orderOfColumns.get(i)][j]) {
                    solvedFields.put(orderOfColumns.get(i), j);
                    for (int k = 0; k < validPairs.length; k++)
                        validPairs[k][j] = false;

                    break;
                }
            }
        }

        return solvedFields;
    }
}
