package Model.Statement;

import Exceptions.InvalidType;
import Exceptions.VariableNotDefined;
import Model.ADT.MyIDict;
import Model.ADT.MyIHeap;
import Model.Expression.IExpression;
import Model.ProgramState;
import Model.Type.IType;
import Model.Type.IntType;
import Model.Type.RefType;
import Model.Value.IValue;
import Model.Value.RefValue;

public class NewStatement implements  IStatement{

    String varName;
    IExpression expression;

    public NewStatement(String varName, IExpression expression)
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

        if (!(programState.getSymbolTable().lookup(this.varName).getType() instanceof RefType))
        {
            throw new InvalidType("Variable " + this.varName + " is not a reference.\n");
        }

        RefType reference = (RefType) programState.getSymbolTable().lookup(this.varName).getType();

        IValue expressionValue;
        try {
            expressionValue = this.expression.evaluate(programState.getSymbolTable(), programState.getHeap());
        }
        catch(Exception e)
        {
            throw e;
        }

        if(! expressionValue.getType().equals(reference.getInner()))
        {
            throw new InvalidType("Expression type does not match the reference inner type.\n");
        }

        MyIHeap<Integer, IValue> heap = programState.getHeap();
        int newAddress = heap.getFreeLocation();
        heap.add(newAddress, expressionValue);

        programState.getSymbolTable().update(this.varName, new RefValue(newAddress, expressionValue.getType()));
        return null;
    }

    @Override
    public MyIDict<String, IType> typeCheck(MyIDict<String, IType> typeEnv) throws Exception
    {
        IType variableType = typeEnv.lookup(this.varName);
        IType expressionType = this.expression.typeCheck(typeEnv);

        if (! variableType.equals(new RefType(expressionType)))
            throw new InvalidType("New: Expression type incompatible with location type.\n");
        return typeEnv;
    }

    @Override
    public String toString()
    {
        return "new(" + this.varName + ", " + this.expression.toString() + ")";
    }
}
