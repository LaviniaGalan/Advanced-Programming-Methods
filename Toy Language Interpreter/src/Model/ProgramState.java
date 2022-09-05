package Model;

import Exceptions.EmptyStack;
import Model.ADT.*;
import Model.Statement.IStatement;
import Model.Type.IType;
import Model.Value.IValue;

import java.io.BufferedReader;

public class ProgramState {
    MyIStack<IStatement> exeStack;
    MyIDict<String, IValue> symbolTable;
    MyIList<IValue> output;
    MyIDict<String, BufferedReader> fileTable;
    MyIHeap<Integer, IValue> heap;
    IStatement program;
    Integer id;

    static Integer currentID = 1;

    public synchronized static int nextID()
    {
        return currentID++;
    }

    public ProgramState(MyIStack<IStatement> exeStack, MyIDict<String, IValue> symbolTable, MyIList<IValue> output, MyIDict<String, BufferedReader> fileTable, MyIHeap<Integer, IValue> heap, IStatement program)
    {
        this.exeStack = exeStack;
        this.symbolTable = symbolTable;
        this.output = output;
        this.fileTable = fileTable;
        this.heap = heap;
        this.program = program;
        this.exeStack.push(program);
        this.id = nextID();
    }

    public MyIStack<IStatement> getExeStack()
    {
        return this.exeStack;
    }

    public MyIDict<String, IValue> getSymbolTable()
    {
        return this.symbolTable;
    }

    public MyIList<IValue> getOutput()
    {
        return this.output;
    }

    public MyIDict<String, BufferedReader> getFileTable() { return this.fileTable;}

    public MyIHeap<Integer, IValue> getHeap() { return this.heap;}

    public Integer getProgramID() {return this.id;}

    public boolean isNotCompleted()
    {
        return !this.exeStack.isEmpty();
    }

    @Override
    public String toString()
    {
        String delimiter = "---";
        return delimiter.repeat(20) +"\n \nID: " + this.id + "\n \nExeStack: \n" + this.exeStack.toString() + "\n \nSymbolTable: \n" +
                this.symbolTable.toString() + "\n \nOutput: \n" + this.output.toString() + "\n \n FileTable: \n" +
                this.fileTable.toString() + "\n \nHeap: \n" + this.heap.toString()+"\n" + delimiter.repeat(20) + "\n\n";
    }


    public ProgramState oneStepExecution() throws Exception {
        if (this.exeStack.isEmpty())
            throw new EmptyStack("Execution stack is empty!\n");

        IStatement statement = this.exeStack.pop();
        return statement.execute(this);
    }
}
