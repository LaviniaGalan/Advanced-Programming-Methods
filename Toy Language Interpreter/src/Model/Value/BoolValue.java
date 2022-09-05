package Model.Value;

import Model.Type.BoolType;
import Model.Type.IType;

public class BoolValue implements  IValue{
    boolean value;

    public BoolValue(boolean value)
    {
        this.value = value;
    }

    public boolean getValue()
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
        return new BoolType();
    }

    @Override
    public boolean equals(Object anotherObject)
    {
        return anotherObject instanceof BoolValue;
    }
}
