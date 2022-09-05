package Model.Statement;

import Exceptions.InvalidType;
import Model.ADT.MyIDict;
import Model.Expression.IExpression;
import Model.ProgramState;
import Model.Type.BoolType;
import Model.Type.IType;
import Model.Value.BoolValue;
import Model.Value.IValue;

public class WhileStatement implements IStatement{

    IExpression expression;
    IStatement statement;

    public WhileStatement(IExpression expression, IStatement statement)
    {
        this.expression = expression;
        this.statement = statement;
    }


    @Override
    public ProgramState execute(ProgramState programState) throws Exception {
        IValue expressionValue;
        expressionValue = this.expression.evaluate(programState.getSymbolTable(), programState.getHeap());
        if (!(expressionValue.getType().equals(new BoolType())))
        {
            throw new InvalidType("The expression is not boolean.\n");
        }

        boolean expressionValueBool = ((BoolValue)expressionValue).getValue();
        if (expressionValueBool)
        {
            programState.getExeStack().push(new WhileStatement(expression, statement));
            programState.getExeStack().push(statement);
        }
        return null;
    }

    @Override
    public MyIDict<String, IType> typeCheck(MyIDict<String, IType> typeEnv) throws Exception
    {
        IType expressionType = this.expression.typeCheck(typeEnv);
        if(! expressionType.equals(new BoolType()))
            throw new InvalidType("IF: Expression is not boolean.\n");
        this.statement.typeCheck(typeEnv.cloneDict());
        return typeEnv;
    }

    @Override
    public String toString()
    {
        return "while(" + expression.toString() + ") execute: " + statement.toString();
    }
}
