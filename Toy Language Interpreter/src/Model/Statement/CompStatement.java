package Model.Statement;

import Model.ADT.MyIDict;
import Model.ADT.MyIStack;
import Model.ProgramState;
import Model.Type.IType;

public class CompStatement implements IStatement{
    IStatement firstStatement;
    IStatement secondStatement;


    public CompStatement(IStatement firstStatement, IStatement secondStatement)
    {
        this.firstStatement = firstStatement;
        this.secondStatement = secondStatement;
    }

    @Override
    public String toString()
    {
        return "(" + this.firstStatement.toString() + ";" + this.secondStatement.toString() + ")";
    }

    @Override
    public ProgramState execute(ProgramState programState) {
        MyIStack<IStatement> exeStack = programState.getExeStack();
        exeStack.push(this.secondStatement);
        exeStack.push(this.firstStatement);
        return null;
    }

    @Override
    public MyIDict<String, IType> typeCheck(MyIDict<String, IType> typeEnv) throws Exception
    {
        return this.secondStatement.typeCheck(firstStatement.typeCheck(typeEnv));
    }
}
