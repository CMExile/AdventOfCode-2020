import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day12 {
    public static void main(String[] args) {
        try {
            File commands = new File("Day12.txt");
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
        int xCoordinate = 0;
        int yCoordinate = 0;
        int currentDirection = 90;

        for (String s : commandList) {
            char command = s.charAt(0);
            int commandValue = Integer.parseInt(s.substring(1));

            switch (command) {
                case 'N': yCoordinate += commandValue; break;
                case 'S': yCoordinate -= commandValue; break;
                case 'E': xCoordinate += commandValue; break;
                case 'W': xCoordinate -= commandValue; break;
                case 'L': currentDirection -= commandValue; break;
                case 'R': currentDirection += commandValue; break;
                case 'F':
                    if (currentDirection == 0)
                        yCoordinate += commandValue;

                    else if (currentDirection == 90)
                        xCoordinate += commandValue;

                    else if (currentDirection == 180)
                        yCoordinate -= commandValue;

                    else if (currentDirection == 270)
                        xCoordinate -= commandValue;

                    break;

                default:
            }

            if (currentDirection >= 360)
                currentDirection -= 360;

            else if (currentDirection < 0)
                currentDirection += 360;
        }
        return (Math.abs(xCoordinate) + Math.abs(yCoordinate));
    }

    public static int PartTwo(List<String> commandList) {
        int xCoordinateShip = 0;
        int yCoordinateShip = 0;
        int xCoordinateWaypoint = 10;
        int yCoordinateWaypoint = 1;

        for (String s : commandList) {
            char command = s.charAt(0);
            int commandValue = Integer.parseInt(s.substring(1));
            int temp;
            switch (command) {
                case 'N': yCoordinateWaypoint += commandValue; break;
                case 'S': yCoordinateWaypoint -= commandValue; break;
                case 'E': xCoordinateWaypoint += commandValue; break;
                case 'W': xCoordinateWaypoint -= commandValue; break;
                case 'L': case 'R':
                    if (commandValue == 90 && command == 'L' || commandValue == 270 && command == 'R') {
                        temp = yCoordinateWaypoint;
                        yCoordinateWaypoint = xCoordinateWaypoint;
                        xCoordinateWaypoint = temp * -1;
                    }
                    else if (commandValue == 180) {
                        xCoordinateWaypoint *= -1;
                        yCoordinateWaypoint *= -1;
                    }
                    else if (commandValue == 270 && command == 'L' || commandValue == 90 && command == 'R') {
                        temp = xCoordinateWaypoint;
                        xCoordinateWaypoint = yCoordinateWaypoint;
                        yCoordinateWaypoint = temp * -1;
                    }

                    break;

                case 'F':
                    xCoordinateShip += (xCoordinateWaypoint * commandValue);
                    yCoordinateShip += (yCoordinateWaypoint * commandValue);
                    break;

                default:
            }
        }

        return (Math.abs(xCoordinateShip) + Math.abs(yCoordinateShip));
    }
}
