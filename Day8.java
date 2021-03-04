import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day8 {
    public static void main(String[] args) {
        try {
            File commands = new File("Day8.txt");
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

    public static int PartOne(List<String> commandList) {
        List<Integer> seenCommands = new ArrayList<>();
        int result = 0;
        int command = 0;
        while (command <= commandList.size() - 1) {
            if (seenCommands.contains(command))
                return result;

            switch (commandList.get(command).split(" ")[0]) {
                case "acc" -> {
                    result += Integer.parseInt(commandList.get(command).split(" ")[1]);
                    seenCommands.add(command);
                    command++;
                }
                case "jmp" -> {
                    seenCommands.add(command);
                    command += Integer.parseInt(commandList.get(command).split(" ")[1]);
                }
                default -> command++;
            }
        }
        return command;
    }

    public static int PartTwo(List<String> commandList) {
        List<String> listToTry = new ArrayList<>(commandList);
        List<Integer> seenCommands = new ArrayList<>();
        int result = 0;
        int command = 0;
        int lastChanged = 0;
        while (listToTry.get(lastChanged).split(" ")[0].equals("acc"))
            lastChanged++;

        if (listToTry.get(lastChanged).split(" ")[0].equals("nop"))
            listToTry.add(lastChanged, "jmp" + " " + listToTry.get(lastChanged).split(" ")[1]);
        else
            listToTry.add(lastChanged, "nop" + " " + listToTry.get(lastChanged).split(" ")[1]);
        listToTry.remove(lastChanged + 1);

        while (command <= listToTry.size() - 1) {

            if (seenCommands.contains(command)) {
                lastChanged++;
                listToTry.clear();
                listToTry.addAll(commandList);
                while (!listToTry.get(lastChanged).split(" ")[0].equals("nop") && !listToTry.get(lastChanged).split(" ")[0].equals("jmp"))
                    lastChanged++;

                if (listToTry.get(lastChanged).split(" ")[0].equals("nop"))
                    listToTry.add(lastChanged, "jmp" + " " + listToTry.get(lastChanged).split(" ")[1]);
                else
                    listToTry.add(lastChanged, "nop" + " " + listToTry.get(lastChanged).split(" ")[1]);
                listToTry.remove(lastChanged + 1);
                seenCommands.clear();
                result = 0;
                command = 0;
            }

            switch (listToTry.get(command).split(" ")[0]) {
                case "acc" -> {
                    result += Integer.parseInt(listToTry.get(command).split(" ")[1]);
                    seenCommands.add(command);
                    command++;
                }
                case "jmp" -> {
                    seenCommands.add(command);
                    command += Integer.parseInt(listToTry.get(command).split(" ")[1]);
                }
                default -> command++;
            }
        }

        return result;
    }
}