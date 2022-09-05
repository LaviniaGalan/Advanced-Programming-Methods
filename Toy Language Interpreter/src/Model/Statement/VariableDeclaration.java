package Model.Statement;

import Exceptions.VariableAlreadyDeclared;
import Model.ADT.MyIDict;
import Model.Expression.VariableExpression;
import Model.ProgramState;
import Model.Type.IType;
import Model.Value.IValue;

public class VariableDeclaration implements IStatement{

    String varName;
    IType varType;

    public VariableDeclaration(String varName, IType varType)
    {
        this.varName = varName;
        this.varType = varType;
    }

    @Override
    public String toString()
    {
        return varType.toString() + " " + varName;
    }


    @Override
    public ProgramState execute(ProgramState programState) throws Exception{
        MyIDict<String, IValue> symbolTable = programState.getSymbolTable();

        if(symbolTable.isDefined(this.varName))
        {
            throw new VariableAlreadyDeclared("Variable " + this.varName + "is already declared.\n");
        }
        else
            symbolTable.add(this.varName, this.varType.getDefaultValue());
        return null;
    }

    @Override
    public MyIDict<String, IType> typeCheck(MyIDict<String, IType> typeEnv) throws Exception
    {
        typeEnv.add(this.varName, this.varType);
        return typeEnv;
    }
}
