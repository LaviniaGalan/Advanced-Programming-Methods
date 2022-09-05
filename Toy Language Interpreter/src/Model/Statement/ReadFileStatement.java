package Model.Statement;

import Exceptions.FileException;
import Exceptions.InvalidType;
import Exceptions.VariableNotDefined;
import Model.ADT.MyIDict;
import Model.Expression.IExpression;
import Model.ProgramState;
import Model.Type.IType;
import Model.Type.IntType;
import Model.Type.StringType;
import Model.Value.IValue;
import Model.Value.IntValue;
import Model.Value.StringValue;

import java.io.BufferedReader;

public class ReadFileStatement implements IStatement {

    String varName;
    IExpression expression;

    public ReadFileStatement(String varName, IExpression expression)
    {
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws Exception {
        if (! programState.getSymbolTable().isDefined(this.varName))
        {
            throw new VariableNotDefined("Variable " + this.varName +" is not declared.\n");
        }

        if (! programState.getSymbolTable().lookup(this.varName).getType().equals(new IntType()))
        {
            throw new InvalidType("Variable " + this.varName + " is not an integer.\n");
        }

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
        if (! programState.getFileTable().isDefined(expressionValueString))
        {
            throw new FileException("The file " + expressionValueString + " is not opened.\n");
        }
        BufferedReader reader = programState.getFileTable().lookup(expressionValueString);
        String readLine = reader.readLine();
        int readValue;
        if(readLine == null) readValue = 0;
        else readValue = Integer.parseInt(readLine);

        programState.getSymbolTable().update(this.varName, new IntValue(readValue));
        return null;
    }

    @Override
    public MyIDict<String, IType> typeCheck(MyIDict<String, IType> typeEnv) throws Exception
    {
        IType variableType = typeEnv.lookup(this.varName);
        IType expressionType = this.expression.typeCheck(typeEnv);

        if(! variableType.equals(new IntType()))
            throw new InvalidType("Read File: Variable " + this.varName + " is not an integer.\n");
        if(! expressionType.equals(new StringType()))
            throw new InvalidType("Read File: Expression is not a string!\n");
        return typeEnv;
    }

    @Override
    public String toString()
    {
        return (this.varName + " = readLine(" + this.expression.toString() + ")");
    }
}
