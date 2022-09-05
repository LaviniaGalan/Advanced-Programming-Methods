package Model.Expression;

import Model.ADT.MyIDict;
import Model.ADT.MyIHeap;
import Model.Type.IType;
import Model.Value.IValue;


public class ValueExpression implements IExpression{

    IValue value;

    public ValueExpression(IValue value)
    {
        this.value = value;
    }


    @Override
    public IValue evaluate(MyIDict<String, IValue> symbolTable, MyIHeap<Integer, IValue> heap)
    {
        return this.value;
    }

    @Override
    public IType typeCheck(MyIDict<String, IType> typeEnv) throws Exception
    {
        return this.value.getType();
    }


    @Override
    public String toString()
    {
        return this.value.toString();
    }

}
