package Model.Statement;

import Model.ADT.MyIDict;
import Model.ProgramState;
import Model.Type.IType;

public class NOPStatement implements IStatement{

    @Override
    public String toString()
    {
        return "Nop";
    }

    @Override
    public ProgramState execute(ProgramState programState) {
        return null;
    }

    @Override
    public MyIDict<String, IType> typeCheck(MyIDict<String, IType> typeEnv) throws Exception {
        return typeEnv;
    }
}
