package Repository;

import Exceptions.FileException;
import Model.ADT.MyList;
import Model.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

public class Repository implements IRepository{
    MyList<ProgramState> programStateList;
    String logFilePath;


    public Repository(String filePath)
    {
        this.programStateList = new MyList<>();
        this.logFilePath = filePath;
    }
    @Override
    public void addProgramState(ProgramState programState)
    {
        this.programStateList.add(programState);
    }

    @Override
    public void logProgramStateExec(ProgramState programState) throws Exception {

        PrintWriter logFile = null;
        try
        {
            logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.logFilePath, true)));
            logFile.write(programState.toString());

        }
        catch (Exception e)
        {
            throw new FileException("An error occurred at writing in file " + this.logFilePath + "!\n");
        }
        finally {
            if (logFile != null)
                try
                {
                    logFile.close();
                }
                catch (Exception e)
                {
                    throw new FileException("An error occurred at closing the file " + this.logFilePath + "!\n");
                }
        }
    }


    @Override
    public void clearFile() throws Exception
    {
        try {
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.logFilePath, false)));
            logFile.write("");
            logFile.close();
        }
        catch(Exception e)
        {
            throw e;
        }
    }

    @Override
    public MyList<ProgramState> getProgramStateList() {
        return this.programStateList;
    }

    @Override
    public void setProgramStateList(List<ProgramState> programStateList) {
        this.programStateList.setContent(programStateList);
    }
}
