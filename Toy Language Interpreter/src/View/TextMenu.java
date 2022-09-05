package View;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu {

    private final Map<String, Command> commands;

    public  TextMenu()
    {
        commands = new HashMap<>();
    }

    public void addCommand(Command command)
    {
        commands.put(command.getKey(), command);
    }

    private void printMenu(){
        System.out.println("Menu:\n");
        for(Command command : commands.values())
        {
            String lineOfMenu = " * " + command.getKey() + " - " + command.getDescription();
            System.out.println(lineOfMenu);
        }
    }

    public void show()
    {
        Scanner scanner = new Scanner(System.in);
        while(true)
        {
            printMenu();
            System.out.print("Input the option: >> ");
            String key = scanner.nextLine();
            Command chosenCommand = commands.get(key);
            if (chosenCommand == null)
            {
                System.out.println("Invalid Option!");
                continue;
            }
            chosenCommand.execute();
        }
    }
}
