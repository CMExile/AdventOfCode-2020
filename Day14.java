import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day14 {
    public static void main(String[] args) {
        try {
            File commands = new File("Day14.txt");
            Scanner sc = new Scanner(commands);
            List<String> commandList = new ArrayList<>();

            while (sc.hasNextLine())
                commandList.add(sc.nextLine());

            System.out.println(PartOne(commandList));
            System.out.println(PartTwo(commandList));
        }

        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static long PartOne(List<String> commandList) {
        long[] memArray = new long[65535];
        String mask = "";

        for (String command : commandList) {
            if (command.startsWith("mask")) {
                mask = command.split("= ")[1];
                continue;
            }

            long nextCalc = Long.parseLong(command.split("= ")[1]);
            StringBuilder answer = new StringBuilder();

            while (nextCalc > 0) {
                if (nextCalc % 2 == 0) {
                    answer.insert(0, '0');
                }

                else {
                    answer.insert(0, '1');
                }

                nextCalc /= 2;
            }

            while (answer.length() < mask.length())
                answer.insert(0, "0");

            for (int i = mask.length() - 1; i >= 0; i--) {
                if (mask.charAt(i) != 'X')
                    answer.replace(i, i + 1, "" + mask.charAt(i));
            }

            memArray[Integer.parseInt(command.split("\\[")[1].split("]")[0])] = Long.parseLong(answer.toString(), 2);
        }

        return Arrays.stream(memArray).reduce(0, Long::sum);
    }

    public static long PartTwo(List<String> commandList) {
        Map<Long, Long> memArray = new HashMap<>();
        String mask = "";

        for (String command : commandList) {
            if (command.startsWith("mask")) {
                mask = command.split("= ")[1];
                continue;
            }

            long valueToUse = Long.parseLong(command.split("= ")[1]);
            long nextCalc = Long.parseLong(command.split("\\[")[1].split("]")[0]);
            StringBuilder answer = new StringBuilder();
            int floatingBitCount = 0;

            while (nextCalc > 0) {
                if (nextCalc % 2 == 0) {
                    answer.insert(0, '0');
                }

                else {
                    answer.insert(0, '1');
                }

                nextCalc /= 2;
            }

            while (answer.length() < mask.length())
                answer.insert(0, "0");

            for (int i = mask.length() - 1; i >= 0; i--) {
                if (mask.charAt(i) == 'X')
                    floatingBitCount++;

                if (mask.charAt(i) != '0')
                    answer.replace(i, i + 1, "" + mask.charAt(i));
            }

            int noOfSolutions = (int)Math.pow(2, floatingBitCount);
            Set<Long> memoryAddresses = indicesToEdit(answer, new HashSet<>(noOfSolutions));

            for (long memoryAddress : memoryAddresses) memArray.put(memoryAddress, valueToUse);
        }
        long result = 0;
        for (Map.Entry<Long, Long> entry : memArray.entrySet())
            result += entry.getValue();

        return result;
    }

    public static Set<Long> indicesToEdit(StringBuilder address, Set<Long> indices) {
        int firstX = address.toString().indexOf('X');

        if (firstX == -1) {
            indices.add(Long.parseLong(address.toString(), 2));
            return indices;
        }
        indicesToEdit(new StringBuilder(address.replace(firstX, firstX + 1, "0").toString()), indices);
        indicesToEdit(new StringBuilder(address.replace(firstX, firstX + 1, "1").toString()), indices);

        return indices;
    }
}
