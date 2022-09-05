package Model.Expression;

import Exceptions.DivisionByZero;
import Exceptions.InvalidType;
import Model.ADT.MyIDict;
import Model.ADT.MyIHeap;
import Model.Type.BoolType;
import Model.Type.IType;
import Model.Type.IntType;
import Model.Value.BoolValue;
import Model.Value.IValue;
import Model.Value.IntValue;

public class RelationalExpression implements IExpression{

    IExpression firstExpression;
    IExpression secondExpression;
    String operator;

    public RelationalExpression(IExpression firstExpression, IExpression secondExpression, String operator)
    {
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
        this.operator = operator;
    }


    @Override
    public IValue evaluate(MyIDict<String, IValue> symbolTable, MyIHeap<Integer, IValue> heap) throws Exception {
        IValue firstValue, secondValue;
        firstValue = this.firstExpression.evaluate(symbolTable, heap);
        if (!(firstValue.getType().equals(new IntType()))) {
            throw new InvalidType("First expression is not integer.\n");
        }
        secondValue = this.secondExpression.evaluate(symbolTable, heap);
        if (!(secondValue.getType().equals(new IntType()))) {
            throw new InvalidType("Second expression is not integer.\n");
        }

        int firstValueInt = ((IntValue) firstValue).getValue();
        int secondValueInt = ((IntValue) secondValue).getValue();
        return switch (this.operator) {
            case "<" -> new BoolValue(firstValueInt < secondValueInt);
            case "<=" -> new BoolValue(firstValueInt <= secondValueInt);
            case "==" -> new BoolValue(firstValueInt == secondValueInt);
            case "!=" -> new BoolValue(firstValueInt != secondValueInt);
            case ">" -> new BoolValue(firstValueInt > secondValueInt);
            case ">=" -> new BoolValue(firstValueInt >= secondValueInt);
            default -> new BoolValue(false);
        };

    }

    @Override
    public IType typeCheck(MyIDict<String, IType> typeEnv) throws Exception
    {
        IType firstType, secondType;

        firstType = this.firstExpression.typeCheck(typeEnv);
        if (! firstType.equals(new IntType()))
           throw new InvalidType("Relational Expression: First expression is not integer.\n");

        secondType = this.secondExpression.typeCheck(typeEnv);
        if (! secondType.equals(new IntType()))
            throw new InvalidType("Relational Expression: Second expression is not integer.\n");

        return new BoolType();
    }


    @Override
    public String toString()
    {
        return this.firstExpression.toString() + " " + this.operator + " " + this.secondExpression.toString();
    }
}
