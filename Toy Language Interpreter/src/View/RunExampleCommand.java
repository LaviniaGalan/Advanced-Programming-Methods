package View;

import Controller.Controller;

public class RunExampleCommand extends Command{
    Controller controller;
    String key, description;

    public RunExampleCommand(String key, String description, Controller controller)
    {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute()
    {
        try
        {
            if (this.controller != null){
                System.out.println("Starting the execution...\n");
                this.controller.completeExecution();
                }
            else
                System.out.println("There's an error at the chosen example; check above.\n");
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }



}
