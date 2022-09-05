package Model.Expression;

import Exceptions.InvalidType;
import Exceptions.VariableNotDefined;
import Model.ADT.MyIDict;
import Model.ADT.MyIHeap;
import Model.Type.IType;
import Model.Value.IValue;

public class VariableExpression implements IExpression{

    String varName;

    public VariableExpression(String varName)
    {
        this.varName = varName;
    }

    @Override
    public IValue evaluate(MyIDict<String, IValue> symbolTable, MyIHeap<Integer, IValue> heap) throws Exception
    {
        if (symbolTable.isDefined(this.varName))
        {
            return symbolTable.lookup(this.varName);
        }
        else
            throw new VariableNotDefined("Variable " + this.varName + " is not defined.\n");
    }

    @Override
    public IType typeCheck(MyIDict<String, IType> typeEnv) throws Exception
    {
        return typeEnv.lookup(this.varName);
    }


    @Override
    public String toString()
    {
        return this.varName;
    }
}
