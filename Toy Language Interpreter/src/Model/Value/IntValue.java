package Model.Value;

import Model.Type.IType;
import Model.Type.IntType;
import Model.Type.StringType;

public class IntValue implements IValue{
    int value;

    public IntValue(int value)
    {
        this.value = value;
    }

    public int getValue()
    {
        return this.value;
    }

    public String toString()
    {
        return String.valueOf(this.value);
    }

    @Override
    public IType getType()
    {
        return new IntType();
    }

    @Override
    public boolean equals(Object anotherObject)
    {
        return anotherObject instanceof IntValue;
    }
}
