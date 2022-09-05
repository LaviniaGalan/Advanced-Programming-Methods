package Model.Type;

import Model.Value.IValue;
import Model.Value.StringValue;

public class StringType implements IType{
    @Override
    public IValue getDefaultValue() {
        return new StringValue("");
    }

    @Override
    public boolean equals(Object anotherObject)
    {
        return anotherObject instanceof StringType;
    }

    @Override
    public String toString()
    {
        return "string";
    }
}
