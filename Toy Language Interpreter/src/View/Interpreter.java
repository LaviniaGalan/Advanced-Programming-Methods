package View;

import java.util.Scanner;

public class Interpreter {

    public static void main(String[] args) {
        Example examples = new Example();

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExampleCommand("1","bool v; v=2;Print(v)", examples.getControllerForExample1()));
        menu.addCommand(new RunExampleCommand("2", "int a;int b; a=2+3*5;b=a+1;Print(b)", examples.getControllerForExample2()));
        menu.addCommand(new RunExampleCommand("3","bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)", examples.getControllerForExample3()));
        menu.addCommand(new RunExampleCommand("4", "int varf; varf=3; openRFile(varf); " +
                "int varc; readFile(varf,varc); print(varc); readFile(varf,varc); print(varc); closeRFile(varf)",
                examples.getControllerForExample4() ));
        menu.addCommand(new RunExampleCommand("5", "int v; v=4; (while (v>0) print(v); v=v-1); print(v)", examples.getControllerForExample5()));
        menu.addCommand(new RunExampleCommand("6", "Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)", examples.getControllerForExample6()));
        menu.addCommand(new RunExampleCommand("7", "Ref string v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);", examples.getControllerForExample7()));
        menu.addCommand(new RunExampleCommand("8", "Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))", examples.getControllerForExample8()));
        menu.addCommand(new RunExampleCommand("9", " int v; Ref int a; v=10;new(a,22); \n\t\tfork(wH(a,30);v=32;print(v);print(rH(a)));\n\t\tprint(v);print(rH(a))", examples.getControllerForExample9()));

        menu.show();
    }

}
