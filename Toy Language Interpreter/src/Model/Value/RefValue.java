package Model.Value;

import Model.Type.IType;
import Model.Type.RefType;

public class RefValue implements IValue {

    int heapAddress;
    IType locationType;

    public RefValue(int address, IType type)
    {
        this.heapAddress = address;
        this.locationType = type;
    }

    public int getHeapAddress()
    {
        return this.heapAddress;
    }

    @Override
    public IType getType() {
        return new RefType(locationType);
    }

    @Override
    public String toString()
    {
        return "(" + heapAddress + ", " + locationType.toString() + ")";
    }
}
