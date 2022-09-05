package View;

import Controller.Controller;
import Model.ADT.*;
import Model.Expression.*;
import Model.ProgramState;
import Model.Statement.*;
import Model.Type.*;
import Model.Value.BoolValue;
import Model.Value.IValue;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Repository.IRepository;
import Repository.Repository;

import java.io.BufferedReader;
import java.util.Arrays;

public class Example {

    public IStatement makeStatement(IStatement... statementsList)
    {
        if(statementsList.length == 0)
            return new NOPStatement();
        if (statementsList.length == 1)
            return statementsList[0];
        return new CompStatement(statementsList[0],
                                 makeStatement(Arrays.copyOfRange(statementsList, 1, statementsList.length)));
    }

    public Controller createController(IStatement statement, String fileName)
    {
        MyIStack<IStatement> exeStack = new MyStack<>();
        MyIDict<String, IValue> symbolTable = new MyDict<>();
        MyIList<IValue> output = new MyList<>();
        MyIDict<String, BufferedReader> fileTable = new MyDict<>();
        MyIHeap<Integer, IValue> heap = new MyHeap<>();

        ProgramState programState = new ProgramState(exeStack, symbolTable, output, fileTable, heap, statement);

        IRepository repository = new Repository(fileName);
        repository.addProgramState(programState);
        return new Controller(repository);
    }

    public Controller getControllerForExample1()
    {
        //bool v; v=2;Print(v)
        IStatement example = makeStatement(new VariableDeclaration("v", new BoolType()),
                                           new AssignStatement("v", new ValueExpression(new IntValue(2))),
                                           new PrintStatement(new VariableExpression("v")));

        MyIDict<String, IType> typeEnv = new MyDict<>();
        try {
            example.typeCheck(typeEnv);
        }
        catch (Exception e)
        {
            System.out.println("\nError at example 1 >> " + e.getMessage());
            return null;
        }

        return createController(example, "Example1.txt");
    }

    public Controller getControllerForExample2()
    {
        //int a;int b; a=2+3*5;b=a+1;Print(b)
        IStatement example = makeStatement(new VariableDeclaration("a", new IntType()),
                                           new VariableDeclaration("b", new IntType()),
                                           new AssignStatement("a", new ArithmeticExpression(new ValueExpression(new IntValue(2)),
                                                                                                    new ArithmeticExpression(new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5)), "*"), "+")),
                                           new AssignStatement("b", new ArithmeticExpression(new VariableExpression("a"), new ValueExpression(new IntValue(1)), "+")),
                                           new PrintStatement(new VariableExpression("b")));

        MyIDict<String, IType> typeEnv = new MyDict<>();
        try {
            example.typeCheck(typeEnv);
        }
        catch (Exception e)
        {
            System.out.println("\nError at example 2 >> " + e.getMessage());
            return null;
        }

        return createController(example, "Example2.txt");
    }


    public Controller getControllerForExample3()
    {
        //bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)
        IStatement example = makeStatement (new VariableDeclaration("a", new BoolType()),
                                            new VariableDeclaration("v", new IntType()),
                                            new AssignStatement("a", new ValueExpression(new BoolValue(true))),
                                            new IfStatement(new VariableExpression("a"),
                                                            new AssignStatement("v", new ValueExpression(new IntValue(2))),
                                                            new AssignStatement("v", new ValueExpression(new IntValue(3)))),
                                            new PrintStatement(new VariableExpression("v")));

        MyIDict<String, IType> typeEnv = new MyDict<>();
        try {
            example.typeCheck(typeEnv);
        }
        catch (Exception e)
        {
            System.out.println("\nError at example 3 >> " + e.getMessage());
            return null;
        }

        return createController(example, "Example3.txt");
    }

    public Controller getControllerForExample4()
    {
        //int varf; varf=3 openRFile(varf);
        //int varc; readFile(varf,varc); print(varc); readFile(varf,varc); print(varc); closeRFile(varf);

        IStatement example = makeStatement (new VariableDeclaration("varf", new IntType()),
                new AssignStatement("varf", new ValueExpression(new IntValue(3))),
                new OpenRFileStatement(new VariableExpression("varf")),
                new VariableDeclaration("varc", new IntType()),
                new ReadFileStatement("varc", new VariableExpression("varf")),
                new PrintStatement(new VariableExpression("varc")),
                new ReadFileStatement("varc", new VariableExpression("varf")),
                new PrintStatement(new VariableExpression("varc")),
                new CloseRFileStatement(new VariableExpression("varf"))
                );

        MyIDict<String, IType> typeEnv = new MyDict<>();
        try {
            example.typeCheck(typeEnv);
        }
        catch (Exception e)
        {
            System.out.println("\nError at example 4 >> " + e.getMessage());
            return null;
        }

        return createController(example, "Example4.txt");
    }

    public Controller getControllerForExample5()
    {
        //int v; v=4; (while (v>0) print(v); v=v-1); print(v)
        IStatement example = makeStatement (new VariableDeclaration("v", new IntType()),
                                            new AssignStatement("v", new ValueExpression(new IntValue(4))),
                                            new WhileStatement(new RelationalExpression(new VariableExpression("v"), new ValueExpression(new IntValue(0)), ">"),
                                                               new CompStatement(new PrintStatement(new VariableExpression("v")),
                                                                                 new AssignStatement("v",
                                                                                       new ArithmeticExpression(new VariableExpression("v"), new ValueExpression(new IntValue(1)), "-")))),
                                            new PrintStatement(new VariableExpression("v"))
        );

        MyIDict<String, IType> typeEnv = new MyDict<>();
        try {
            example.typeCheck(typeEnv);
        }
        catch (Exception e)
        {
            System.out.println("\nError at example 5 >> " + e.getMessage());
            return null;
        }

        return createController(example, "Example5.txt");
    }

    public Controller getControllerForExample6()
    {
        //Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)

        IStatement example = makeStatement(
                new VariableDeclaration("v", new RefType(new IntType())),
                new NewStatement("v", new ValueExpression(new IntValue(20))),
                new VariableDeclaration("a", new RefType(new RefType(new IntType()))),
                new NewStatement("a", new VariableExpression("v")),
                new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                new PrintStatement(new ArithmeticExpression(new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a"))),
                                                            new ValueExpression(new IntValue(5)),
                                                            "+"))
                );
        MyIDict<String, IType> typeEnv = new MyDict<>();
        try {
            example.typeCheck(typeEnv);
        }
        catch (Exception e)
        {
            System.out.println("\nError at example 6 >> " + e.getMessage());
            return null;
        }

        return createController(example, "Example6.txt");
    }

    public Controller getControllerForExample7()
    {
        //Ref String v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);
        IStatement example = makeStatement(
                new VariableDeclaration("v", new RefType(new BoolType())),
                new NewStatement("v", new ValueExpression(new IntValue(20))),
                new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                new WriteHeapStatement("v", new ValueExpression(new IntValue(30))),
                new PrintStatement(new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("v")),
                                   new ValueExpression(new IntValue(5)),
                                   "+"))
        );
        MyIDict<String, IType> typeEnv = new MyDict<>();
        try {
            example.typeCheck(typeEnv);
        }
        catch (Exception e)
        {
            System.out.println("\nError at example 7 >> " + e.getMessage());
            return null;
        }

        return createController(example, "Example7.txt");
    }


    public Controller getControllerForExample8()
    {
        //Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))

        IStatement example = makeStatement(
                new VariableDeclaration("v", new RefType(new IntType())),
                new NewStatement("v", new ValueExpression(new IntValue(20))),
                new VariableDeclaration("a", new RefType(new RefType(new IntType()))),
                new NewStatement("a", new VariableExpression("v")),
                new NewStatement("v", new ValueExpression(new IntValue(30))),
                new PrintStatement(new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a"))))
        );

        MyIDict<String, IType> typeEnv = new MyDict<>();
        try {
            example.typeCheck(typeEnv);
        }
        catch (Exception e)
        {
            System.out.println("\nError at example 8 >> " + e.getMessage());
            return null;
        }

        return createController(example, "Example8.txt");
    }

    public Controller getControllerForExample9()
    {
        //int v; Ref int a; v=10;new(a,22);
        //  fork(wH(a,30);v=32;print(v);print(rH(a)));
        //print(v);print(rH(a));

        IStatement example = makeStatement(
                new VariableDeclaration("v", new IntType()),
                new VariableDeclaration("a", new RefType(new IntType())),
                new AssignStatement("v", new ValueExpression(new IntValue(10))),
                new NewStatement("a", new ValueExpression(new IntValue(22))),

                new ForkStatement(makeStatement(new WriteHeapStatement("a", new ValueExpression(new IntValue(30))),
                                                new AssignStatement("v", new ValueExpression(new IntValue(32))),
                                                new PrintStatement(new VariableExpression("v")),
                                                new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
                                               )),
                new PrintStatement(new VariableExpression("v")),
                new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
        );

        MyIDict<String, IType> typeEnv = new MyDict<>();
        try {
            example.typeCheck(typeEnv);
        }
        catch (Exception e)
        {
            System.out.println("\nError at example 9 >> " + e.getMessage());
            return null;
        }

        return createController(example, "Example9.txt");
    }
}
