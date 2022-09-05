package Model.Expression;

import Exceptions.InvalidType;
import Model.ADT.MyDict;
import Model.ADT.MyIDict;
import Model.ADT.MyIHeap;
import Model.Type.BoolType;
import Model.Type.IType;
import Model.Value.BoolValue;
import Model.Value.IValue;

public class LogicalExpression implements IExpression{
    IExpression firstExpression;
    IExpression secondExpression;
    String operator;

    public LogicalExpression(IExpression firstExpression, IExpression secondExpression, String operator)
    {
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
        this.operator = operator;
    }

    @Override
    public String toString()
    {
        return "(" + this.firstExpression.toString() + " " + this.operator + " " + this.secondExpression.toString() + ")";
    }

    @Override
    public IValue evaluate(MyIDict<String, IValue> symbolTable, MyIHeap<Integer, IValue> heap) throws Exception {
        IValue firstValue, secondValue;
        firstValue = this.firstExpression.evaluate(symbolTable, heap);
        if (!(firstValue.getType().equals(new BoolType())))
        {
            throw new InvalidType("First expression is not boolean.\n");
        }

        secondValue = this.secondExpression.evaluate(symbolTable, heap);
        if (!(secondValue.getType().equals(new BoolType())))
        {
            throw new InvalidType("Second expression is not boolean.\n");
        }

        boolean firstValueBool = ((BoolValue)firstValue).getValue();
        boolean secondValueBool = ((BoolValue)secondValue).getValue();
        return switch (this.operator) {
            case "&&" -> new BoolValue(firstValueBool && secondValueBool);
            case "||" -> new BoolValue(firstValueBool || secondValueBool);
            default -> new BoolValue(false);
        };
    }

    @Override
    public IType typeCheck(MyIDict<String, IType> typeEnv) throws Exception
    {
        IType firstType, secondType;

        firstType = this.firstExpression.typeCheck(typeEnv);
        if (! firstType.equals(new BoolType()))
            throw new InvalidType("Logical Expression: First expression is not boolean.\n");

        secondType = this.secondExpression.typeCheck(typeEnv);
        if (! secondType.equals(new BoolType()))
            throw new InvalidType("Logical Expression: Second expression is not boolean.\n");

        return new BoolType();
    }


}
