package Model.Type;

import Model.Value.IValue;
import Model.Value.IntValue;

public class IntType implements IType{

    @Override
    public boolean equals(Object anotherObject)
    {
        return anotherObject instanceof IntType;
    }

    @Override
    public String toString()
    {
        return "int";
    }

    @Override
    public IValue getDefaultValue() {
        return new IntValue(0);
    }
}
