package Model.Statement;

import Exceptions.InvalidType;
import Exceptions.VariableNotDefined;
import Model.ADT.MyIDict;
import Model.ADT.MyIStack;
import Model.Expression.IExpression;
import Model.ProgramState;
import Model.Type.IType;
import Model.Value.IValue;

public class AssignStatement implements IStatement{

    String varName;
    IExpression expression;

    public AssignStatement(String varName, IExpression expression)
    {
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws Exception
    {

        MyIDict<String, IValue> symbolTable = programState.getSymbolTable();

        if (symbolTable.isDefined(this.varName))
        {
            IValue expressionValue = this.expression.evaluate(symbolTable, programState.getHeap());
            IType varType = (symbolTable.lookup(varName)).getType();

            if (expressionValue.getType().equals(varType))
            {
                symbolTable.update(varName, expressionValue);
            }
            else
                throw new InvalidType("Expression type incompatible with variable type.\n");
        }
        else
            throw new VariableNotDefined("Variable " + this.varName + " is not defined.\n");
        return null;
    }

    @Override
    public MyIDict<String, IType> typeCheck(MyIDict<String, IType> typeEnv) throws Exception {
        IType variableType = typeEnv.lookup(this.varName);
        IType expressionType = this.expression.typeCheck(typeEnv);

        if (! variableType.equals(expressionType))
            throw new InvalidType("Assignment: Expression type incompatible with variable type.\n");

        return typeEnv;
    }

    @Override
    public String toString()
    {
        return this.varName + " = " + this.expression.toString();
    }
}
