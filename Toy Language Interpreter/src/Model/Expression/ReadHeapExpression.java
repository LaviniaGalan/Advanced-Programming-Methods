package Model.Expression;

import Exceptions.InvalidAddress;
import Exceptions.InvalidType;
import Model.ADT.MyIDict;
import Model.ADT.MyIHeap;
import Model.Type.IType;
import Model.Type.RefType;
import Model.Value.IValue;
import Model.Value.IntValue;
import Model.Value.RefValue;

public class ReadHeapExpression implements IExpression{

    IExpression expression;

    public ReadHeapExpression(IExpression expression)
    {
        this.expression = expression;
    }

    @Override
    public IValue evaluate(MyIDict<String, IValue> symbolTable, MyIHeap<Integer, IValue> heap) throws Exception {
        IValue evaluatedExpression;
        try
        {
            evaluatedExpression = this.expression.evaluate(symbolTable, heap);

        }
        catch (Exception e)
        {
            throw e;
        }
        if(!(evaluatedExpression instanceof RefValue))
        {
            throw new InvalidType("Expression could not be evaluated to a Ref Value!\n");
        }

        Integer address = ((RefValue)evaluatedExpression).getHeapAddress();
        if (!heap.isDefined(address))
        {
            throw new InvalidAddress("The address is not a ket in Heap table!\n");
        }
        else
            return heap.lookup(address);
    }

    @Override
    public IType typeCheck(MyIDict<String, IType> typeEnv) throws Exception
    {
        IType expressionType = this.expression.typeCheck(typeEnv);
        if (! (expressionType instanceof RefType))
            throw new InvalidType("Read Heap: Expression is not a Ref Type!\n");

        return ((RefType) expressionType).getInner();
    }


    @Override
    public String toString()
    {
        return "rH(" + this.expression.toString() + ")";
    }
}
