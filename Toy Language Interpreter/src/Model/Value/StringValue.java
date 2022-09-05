package Model.Value;

import Model.Type.IType;
import Model.Type.StringType;

public class StringValue implements IValue{
    String stringValue;

    public StringValue(String stringValue)
    {
        this.stringValue = stringValue;
    }

    @Override
    public IType getType() {
        return new StringType();
    }

    public String getValue()
    {
        return this.stringValue;
    }

    public String toString()
    {
        return this.stringValue;
    }

    @Override
    public boolean equals(Object anotherObject)
    {
        return anotherObject instanceof StringValue;
    }
}
