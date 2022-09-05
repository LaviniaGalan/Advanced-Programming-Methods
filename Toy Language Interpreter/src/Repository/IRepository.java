package Repository;

import Model.ADT.MyList;
import Model.ProgramState;

import java.util.List;

public interface IRepository {

    void addProgramState(ProgramState programState);

    void logProgramStateExec(ProgramState programState) throws Exception;
    void clearFile() throws Exception;

    MyList<ProgramState> getProgramStateList();
    void setProgramStateList(List<ProgramState> programStateList);
}
