package Model.Statement;

import Model.ADT.MyIDict;
import Model.ProgramState;
import Model.Type.IType;

public interface IStatement {

    ProgramState execute(ProgramState programState) throws Exception;

    MyIDict<String, IType> typeCheck(MyIDict<String, IType> typeEnv) throws Exception;
}
