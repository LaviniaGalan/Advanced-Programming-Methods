package Model.Expression;

import Exceptions.DivisionByZero;
import Exceptions.InvalidType;
import Model.ADT.MyDict;
import Model.ADT.MyIDict;
import Model.ADT.MyIHeap;
import Model.Type.IType;
import Model.Type.IntType;
import Model.Value.IValue;
import Model.Value.IntValue;

public class ArithmeticExpression implements IExpression {
    IExpression firstExpression;
    IExpression secondExpression;
    String operator;

    static MyIDict<Integer, String> operatorsDict = new MyDict<>();

    public ArithmeticExpression(IExpression firstExpression, IExpression secondExpression, String operator) {
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
        this.operator = operator;
    }

    @Override
    public String toString() {
        return "(" + firstExpression.toString() + " " + this.operator + " " + secondExpression.toString() + ")";
    }

    @Override
    public IValue evaluate(MyIDict<String, IValue> symbolTable, MyIHeap<Integer, IValue> heap) throws Exception{
        IValue firstValue, secondValue;
        firstValue = this.firstExpression.evaluate(symbolTable,  heap);
        if (!(firstValue.getType().equals(new IntType()))) {
            throw new InvalidType("First expression is not integer.\n");
        }
        secondValue = this.secondExpression.evaluate(symbolTable, heap);
        if (!(secondValue.getType().equals(new IntType()))) {
            throw new InvalidType("Second expression is not integer.\n");
        }

        int firstValueInt = ((IntValue) firstValue).getValue();
        int secondValueInt = ((IntValue) secondValue).getValue();
        switch (this.operator) {
            case "+":
                return new IntValue(firstValueInt + secondValueInt);
            case "-":
                return new IntValue(firstValueInt - secondValueInt);
            case "*":
                return new IntValue(firstValueInt * secondValueInt);
            case "/":
                if (secondValueInt == 0)
                    throw new DivisionByZero("Division by zero.\n");
                return new IntValue(firstValueInt / secondValueInt);
            default:
                return new IntValue(0);
        }
    }

    @Override
    public IType typeCheck(MyIDict<String, IType> typeEnv) throws Exception
    {
        IType firstType, secondType;

        firstType = this.firstExpression.typeCheck(typeEnv);
        if (! firstType.equals(new IntType()))
            throw new InvalidType("Arithmetic Expression: First expression is not integer.\n");

        secondType = this.secondExpression.typeCheck(typeEnv);
        if (! secondType.equals(new IntType()))
            throw new InvalidType("Arithmetic Expression: Second expression is not integer.\n");

        return new IntType();
    }

}
