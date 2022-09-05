package Model.Statement;

import Exceptions.InvalidType;
import Model.ADT.MyIDict;
import Model.ADT.MyIStack;
import Model.Expression.IExpression;
import Model.ProgramState;
import Model.Type.BoolType;
import Model.Type.IType;
import Model.Value.BoolValue;
import Model.Value.IValue;

public class IfStatement implements IStatement{
    IExpression expression;
    IStatement thenStatement;
    IStatement elseStatement;

    public IfStatement(IExpression expression, IStatement thenStatement, IStatement elseStatement)
    {
        this.expression = expression;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }


    @Override
    public String toString()
    {
        return "IF(" + this.expression.toString() + ") THEN " + this.thenStatement.toString() + " ELSE " + this.elseStatement.toString();
    }

    @Override
    public ProgramState execute(ProgramState programState) throws Exception
    {
        MyIStack<IStatement> exeStack = programState.getExeStack();

        IValue expressionValue = this.expression.evaluate(programState.getSymbolTable(), programState.getHeap());
        if (! expressionValue.getType().equals(new BoolType()))
        {
            throw new InvalidType("Expression is not boolean.\n");
        }
        boolean expressionValueBool = ((BoolValue)expressionValue).getValue();
        if (expressionValueBool)
        {
            exeStack.push(this.thenStatement);
        }
        else
            exeStack.push(this.elseStatement);
        return null;
    }

    @Override
    public MyIDict<String, IType> typeCheck(MyIDict<String, IType> typeEnv) throws Exception
    {
        IType expressionType = this.expression.typeCheck(typeEnv);
        if(! expressionType.equals(new BoolType()))
            throw new InvalidType("IF: Expression is not boolean.\n");
        this.thenStatement.typeCheck(typeEnv.cloneDict());
        this.elseStatement.typeCheck(typeEnv.cloneDict());
        return typeEnv;
    }
}
