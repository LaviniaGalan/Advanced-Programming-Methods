package Model.Type;

import Model.Value.BoolValue;
import Model.Value.IValue;

public class BoolType implements IType {


    @Override
    public boolean equals(Object anotherObject)
    {
        return anotherObject instanceof BoolType;
    }

    @Override
    public String toString()
    {
        return "bool";
    }

    @Override
    public IValue getDefaultValue() {
        return new BoolValue(false);
    }
}
