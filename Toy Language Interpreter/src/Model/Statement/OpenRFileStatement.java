package Model.Statement;

import Exceptions.FileException;
import Exceptions.InvalidType;
import Model.ADT.MyIDict;
import Model.Expression.IExpression;
import Model.ProgramState;
import Model.Type.IType;
import Model.Type.StringType;
import Model.Value.IValue;
import Model.Value.StringValue;

import java.io.BufferedReader;
import java.io.FileReader;

public class OpenRFileStatement implements IStatement{

    IExpression expression;
    public OpenRFileStatement(IExpression expression)
    {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws Exception {

        IValue expressionValue;
        try {
            expressionValue = this.expression.evaluate(programState.getSymbolTable(), programState.getHeap());
        }
        catch(Exception e)
        {
            throw e;
        }
        if (! expressionValue.getType().equals(new StringType()))
        {
            throw new InvalidType("Expression is not a string!\n");
        }

        String expressionValueString = ((StringValue)expressionValue).getValue();
        if (programState.getFileTable().isDefined(expressionValueString))
        {
            throw new FileException("The file " + expressionValueString + " is already opened.\n");
        }

        BufferedReader reader = null;
        try
        {
            reader = new BufferedReader(new FileReader(expressionValueString));
            programState.getFileTable().add(expressionValueString, reader);
        }
        catch (Exception e)
        {
            throw new FileException("An error occurred at opening the file " + expressionValueString + "!\n");
        }

        return null;
    }

    @Override
    public MyIDict<String, IType> typeCheck(MyIDict<String, IType> typeEnv) throws Exception
    {
        IType expressionType = this.expression.typeCheck(typeEnv);
        if (! expressionType.equals(new StringType()))
            throw new InvalidType("Open File: Expression is not a string!\n");
        return typeEnv;
    }

    @Override
    public String toString()
    {
        return ("Open(" + this.expression.toString() + ")");
    }
}
