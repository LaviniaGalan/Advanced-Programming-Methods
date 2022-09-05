package Model.Type;

import Model.Value.IValue;
import Model.Value.RefValue;

public class RefType implements IType{

    IType inner;

    public RefType(IType inner)
    {
        this.inner = inner;
    }

    public IType getInner()
    {
        return this.inner;
    }

    @Override
    public boolean equals(Object anotherObject)
    {
        if(anotherObject instanceof RefType)
            return inner.equals(((RefType)anotherObject).getInner());
        return false;
    }

    @Override
    public String toString()
    {
        return "Ref (" + this.inner.toString() + ")";
    }

    @Override
    public IValue getDefaultValue() {
        return new RefValue(0, inner);
    }
}
