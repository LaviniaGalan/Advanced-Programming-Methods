package Model.Statement;

import Model.ADT.MyIDict;
import Model.ADT.MyIList;
import Model.Expression.IExpression;
import Model.ProgramState;
import Model.Type.IType;
import Model.Value.IValue;

public class PrintStatement implements IStatement{

    IExpression expression;
    public PrintStatement(IExpression expression)
    {
        this.expression = expression;
    }

    @Override
    public String toString()
    {
        return "print(" + this.expression.toString() + ")";
    }

    @Override
    public ProgramState execute(ProgramState programState) throws Exception
    {
        MyIList<IValue> output = programState.getOutput();
        try {
            output.add(this.expression.evaluate(programState.getSymbolTable(), programState.getHeap()));
            return null;
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    @Override
    public MyIDict<String, IType> typeCheck(MyIDict<String, IType> typeEnv) throws Exception
    {
        this.expression.typeCheck(typeEnv);
        return typeEnv;
    }
}
