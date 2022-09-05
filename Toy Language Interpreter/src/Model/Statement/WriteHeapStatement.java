package Model.Statement;

import Exceptions.InvalidAddress;
import Exceptions.InvalidType;
import Exceptions.VariableNotDefined;
import Model.ADT.MyIDict;
import Model.Expression.IExpression;
import Model.ProgramState;
import Model.Type.IType;
import Model.Type.RefType;
import Model.Value.IValue;
import Model.Value.RefValue;

public class WriteHeapStatement implements IStatement {
    String varName;
    IExpression expression;

    public WriteHeapStatement(String varName, IExpression expression)
    {
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws Exception {
        if (!programState.getSymbolTable().isDefined(this.varName))
        {
            throw new VariableNotDefined("Variable " + this.varName + " is not defined.\n");
        }
        IValue variableValue = programState.getSymbolTable().lookup(this.varName);
        if (! (variableValue.getType() instanceof RefType))
        {
            throw new InvalidType("The variable has not a Ref type!\n");
        }

        Integer address = ((RefValue)variableValue).getHeapAddress();
        if (!programState.getHeap().isDefined(address))
        {
            throw new InvalidAddress("The address is not in the heap.\n");
        }

        IValue evaluatedExpression;
        try
        {
            evaluatedExpression = this.expression.evaluate(programState.getSymbolTable(), programState.getHeap());
        }
        catch (Exception e)
        {
            throw e;
        }

        IType innerType = ((RefType)variableValue.getType()).getInner();
        if(!evaluatedExpression.getType().equals(innerType))
        {
            throw new InvalidType("Expression type does not match the location type!\n");
        }

        programState.getHeap().update(address, evaluatedExpression);
        return null;
    }

    @Override
    public MyIDict<String, IType> typeCheck(MyIDict<String, IType> typeEnv) throws Exception
    {
        IType variableType = typeEnv.lookup(this.varName);
        IType expressionType = this.expression.typeCheck(typeEnv);

        if (! variableType.equals(new RefType(expressionType)))
            throw new InvalidType("Write Heap: Expression type incompatible with location type.\n");
        return typeEnv;
    }

    @Override
    public String toString() {
        return "wH("+ this.varName +", "+ expression.toString()+")";
    }
}

