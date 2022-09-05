package Model.Expression;

import Model.ADT.MyIDict;
import Model.ADT.MyIHeap;
import Model.Type.IType;
import Model.Value.IValue;

public interface IExpression {

    IValue evaluate(MyIDict<String, IValue> symbolTable, MyIHeap<Integer, IValue> heap) throws Exception;

    IType typeCheck(MyIDict<String,IType> typeEnv) throws Exception;
    String toString();
}
