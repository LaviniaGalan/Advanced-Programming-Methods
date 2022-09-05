package Controller;

import Model.ADT.MyIStack;
import Model.ProgramState;
import Model.Statement.IStatement;
import Model.Value.IValue;
import Model.Value.RefValue;
import Repository.IRepository;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {
    IRepository repository;
    boolean displayFlag;
    ExecutorService executor;

    public Controller(IRepository repository) {
        this.repository = repository;
        this.displayFlag = true;
    }


    Map<Integer, IValue> garbageCollector(List<Integer> usedAddresses, Map<Integer,IValue> heap)
    {
        return heap.entrySet().stream()
                .filter(e -> usedAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    List<Integer> getUsedAddresses(Collection<IValue> symTableValues, Collection<IValue> heapValues)
    {
        return Stream.concat(
                symTableValues.stream()
                        .filter(v -> v instanceof RefValue)
                        .map(v-> {RefValue v1 = (RefValue)v; return v1.getHeapAddress();}),

                heapValues.stream()
                        .filter(v -> v instanceof RefValue)
                        .map(v-> {RefValue v1 = (RefValue)v; return v1.getHeapAddress();}))
                .collect(Collectors.toList());
    }


    public List<ProgramState> removeCompletedPrograms(List<ProgramState> programStateList)
    {
        return programStateList.stream().
                filter(ProgramState::isNotCompleted).
                collect(Collectors.toList());
    }

    public boolean isProgramFinished()
    {
        return repository.getProgramStateList().isEmpty();
    }

    public void oneStepForAllPrograms(List<ProgramState> programStateList) throws Exception
    {

        List<Callable<ProgramState>> callList = programStateList.stream().
                map((ProgramState programState) ->  (Callable<ProgramState>)(programState::oneStepExecution)).
                collect(Collectors.toList());

        try {
            List<ProgramState> newProgramStateList = this.executor.invokeAll(callList).stream().
                    map(future ->
                    {
                        try {
                            return future.get();
                        } catch (Exception e) {
                            throw new RuntimeException(e.getMessage());
                        }
                    }).
                    filter(Objects::nonNull).
                    collect(Collectors.toList());

            programStateList.addAll(newProgramStateList);
            programStateList.forEach(programState -> {
                try {
                    this.repository.logProgramStateExec(programState);
                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage());
                }
            });
            this.repository.setProgramStateList(programStateList);
        }
        catch (InterruptedException e)
        {
            throw new RuntimeException(e.getMessage());
        }

    }

    public void executeOneStep() throws Exception
    {
        //this.repository.clearFile();

        this.executor = Executors.newFixedThreadPool(2);
        List<ProgramState> allPrograms = this.removeCompletedPrograms(this.repository.getProgramStateList().getContent());

        if (allPrograms.isEmpty())
        {
            throw new Exception("Program has finished!");
        }

        allPrograms.forEach(programState -> {
            try {
                this.repository.logProgramStateExec(programState);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        });
        oneStepForAllPrograms(allPrograms);

        //garbage collector:
        Collection<IValue> allSymbolTable = new ArrayList<>();
        allPrograms.forEach(programState -> allSymbolTable.addAll(programState.getSymbolTable().getValues()));
        ProgramState programState = allPrograms.get(0);
        programState.getHeap().setContent(garbageCollector(getUsedAddresses(allSymbolTable,
                programState.getHeap().getValues()),
                programState.getHeap().getContent()));


        this.executor.shutdownNow();
        this.repository.setProgramStateList(allPrograms);
    }

    public void completeExecution() throws Exception {
        this.repository.clearFile();

        this.executor = Executors.newFixedThreadPool(2);
        List<ProgramState> allPrograms = this.removeCompletedPrograms(this.repository.getProgramStateList().getContent());

        allPrograms.forEach(programState -> {
            try {
                this.repository.logProgramStateExec(programState);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        });

        while (allPrograms.size() > 0)
        {
            oneStepForAllPrograms(allPrograms);

            //garbage collector:
            Collection<IValue> allSymbolTable = new ArrayList<>();
            allPrograms.forEach(programState -> allSymbolTable.addAll(programState.getSymbolTable().getValues()));
            ProgramState programState = allPrograms.get(0);
            programState.getHeap().setContent(garbageCollector(getUsedAddresses(allSymbolTable,
                    programState.getHeap().getValues()),
                    programState.getHeap().getContent()));

            allPrograms = this.removeCompletedPrograms(this.repository.getProgramStateList().getContent());
        }
        this.executor.shutdownNow();
        this.repository.setProgramStateList(allPrograms);
    }

    public List<String> getOutputList()
    {
        return repository.getProgramStateList().getElementFromPosition(0).getOutput().getContent().stream().
                map(Objects::toString). collect(Collectors.toList());
    }

    public List<String> getFileTable()
    {
        return repository.getProgramStateList().getElementFromPosition(0).getFileTable().getContent().keySet().stream().
                map(Objects::toString). collect(Collectors.toList());
    }

    public Map<Integer, IValue> getHeap()
    {
        return this.repository.getProgramStateList().getElementFromPosition(0).getHeap().getContent();
    }

    public List<Integer> getProgramStateIDs()
    {
        return repository.getProgramStateList().getContent().stream().map(ProgramState::getProgramID).collect(Collectors.toList());
    }

    public Integer getCurrentProgramStateID()
    {
        return repository.getProgramStateList().getElementFromPosition(0).getProgramID();
    }

    public ProgramState getProgramByIndex(int index)
    {
        return repository.getProgramStateList().getElementFromPosition(index);
    }
}

