package Model.Statement;

import Model.ADT.*;
import Model.ProgramState;
import Model.Type.IType;
import Model.Value.IValue;

import java.io.BufferedReader;

public class ForkStatement implements IStatement{

    IStatement statement;

    public ForkStatement(IStatement statement)
    {
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws Exception {
        MyIHeap<Integer, IValue> heap = programState.getHeap();

        MyIDict<String, BufferedReader> fileTable = programState.getFileTable();

        MyIDict<String, IValue> symbolTable = programState.getSymbolTable().cloneDict();

        MyIStack<IStatement> exeStack = new MyStack<>();

        MyIList<IValue> outputList = programState.getOutput();

        return new ProgramState(exeStack, symbolTable, outputList, fileTable, heap, this.statement);
    }

    @Override
    public MyIDict<String, IType> typeCheck(MyIDict<String, IType> typeEnv) throws Exception
    {
        this.statement.typeCheck(typeEnv.cloneDict());
        return typeEnv;
    }

    @Override
    public String toString()
    {
        return "fork(" + this.statement.toString() + ")";
    }
}
