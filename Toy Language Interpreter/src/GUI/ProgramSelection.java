package GUI;

import Controller.Controller;
import Model.ADT.*;
import Model.Expression.*;
import Model.ProgramState;
import Model.Statement.*;
import Model.Type.*;
import Model.Value.BoolValue;
import Model.Value.IValue;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Repository.IRepository;
import Repository.Repository;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;

import java.io.BufferedReader;
import java.net.URL;
import java.util.*;

public class ProgramSelection implements Initializable {
    @FXML
    private ListView<String> programListView;
    @FXML
    private Button executeButton;

    private List<IStatement> programStatementList;
    private List<String> programAsStringList;

    private ProgramExecution programExecutionWindow;

    public IStatement makeStatement(IStatement... statementsList)
    {
        if(statementsList.length == 0)
            return new NOPStatement();
        if (statementsList.length == 1)
            return statementsList[0];
        return new CompStatement(statementsList[0],
                makeStatement(Arrays.copyOfRange(statementsList, 1, statementsList.length)));
    }


    public void populateProgramList()
    {
        this.programStatementList = new ArrayList<>();
        this.programAsStringList = new ArrayList<>();
        //int v; v=2; Print(v)
        IStatement example1 = makeStatement(new VariableDeclaration("v", new IntType()),
                new AssignStatement("v", new ValueExpression(new IntValue(2))),
                new PrintStatement(new VariableExpression("v")));
        this.programAsStringList.add("int v; v=2; Print(v)");
        this.programStatementList.add(example1);

        //int a;int b; a=2+3*5;b=a+1;Print(b)
        IStatement example2 = makeStatement(new VariableDeclaration("a", new IntType()),
                new VariableDeclaration("b", new IntType()),
                new AssignStatement("a", new ArithmeticExpression(new ValueExpression(new IntValue(2)),
                        new ArithmeticExpression(new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5)), "*"), "+")),
                new AssignStatement("b", new ArithmeticExpression(new VariableExpression("a"), new ValueExpression(new IntValue(1)), "+")),
                new PrintStatement(new VariableExpression("b")));
        this.programAsStringList.add("int a; int b; a=2+3*5; b=a+1; Print(b)");
        this.programStatementList.add(example2);

        //bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)
        IStatement example3 = makeStatement (new VariableDeclaration("a", new BoolType()),
                new VariableDeclaration("v", new IntType()),
                new AssignStatement("a", new ValueExpression(new BoolValue(true))),
                new IfStatement(new VariableExpression("a"),
                        new AssignStatement("v", new ValueExpression(new IntValue(2))),
                        new AssignStatement("v", new ValueExpression(new IntValue(3)))),
                new PrintStatement(new VariableExpression("v")));
        this.programAsStringList.add("bool a; int v; a=true; (If a Then v=2 Else v=3); Print(v)");
        this.programStatementList.add(example3);

        //String varf; varf="Test.in"; openRFile(varf);
        //int varc; readFile(varf,varc); print(varc); readFile(varf,varc); print(varc); closeRFile(varf);
        IStatement example4 = makeStatement (new VariableDeclaration("varf", new StringType()),
                new AssignStatement("varf", new ValueExpression(new StringValue("Test.in"))),
                new OpenRFileStatement(new VariableExpression("varf")),
                new VariableDeclaration("varc", new IntType()),
                new ReadFileStatement("varc", new VariableExpression("varf")),
                new PrintStatement(new VariableExpression("varc")),
                new ReadFileStatement("varc", new VariableExpression("varf")),
                new PrintStatement(new VariableExpression("varc")),
                new CloseRFileStatement(new VariableExpression("varf"))
        );
        this.programAsStringList.add("String varf; varf=\"Test.in\"; openRFile(varf);\n" +
                "int varc; readFile(varf,varc); print(varc); readFile(varf,varc); print(varc);\ncloseRFile(varf);");
        this.programStatementList.add(example4);


        //int v; v=4; (while (v>0) print(v); v=v-1); print(v)
        IStatement example5 = makeStatement (new VariableDeclaration("v", new IntType()),
                new AssignStatement("v", new ValueExpression(new IntValue(4))),
                new WhileStatement(new RelationalExpression(new VariableExpression("v"), new ValueExpression(new IntValue(0)), ">"),
                        new CompStatement(new PrintStatement(new VariableExpression("v")),
                                new AssignStatement("v",
                                        new ArithmeticExpression(new VariableExpression("v"), new ValueExpression(new IntValue(1)), "-")))),
                new PrintStatement(new VariableExpression("v"))
        );
        this.programAsStringList.add("int v; v=4; (while (v>0) print(v); v=v-1); Print(v)");
        this.programStatementList.add(example5);


        //Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)

        IStatement example6 = makeStatement(
                new VariableDeclaration("v", new RefType(new IntType())),
                new NewStatement("v", new ValueExpression(new IntValue(20))),
                new VariableDeclaration("a", new RefType(new RefType(new IntType()))),
                new NewStatement("a", new VariableExpression("v")),
                new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                new PrintStatement(new ArithmeticExpression(new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a"))),
                        new ValueExpression(new IntValue(5)),
                        "+"))
        );
        this.programAsStringList.add("Ref int v;new(v,20);Ref Ref int a; new(a,v); Print(rH(v)); Print(rH(rH(a))+5)");
        this.programStatementList.add(example6);

        //Ref String v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);
        IStatement example7 = makeStatement(
                new VariableDeclaration("v", new RefType(new BoolType())),
                new NewStatement("v", new ValueExpression(new IntValue(20))),
                new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                new WriteHeapStatement("v", new ValueExpression(new IntValue(30))),
                new PrintStatement(new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("v")),
                        new ValueExpression(new IntValue(5)),
                        "+"))
        );
        this.programAsStringList.add("Ref String v; new(v,20); Print(rH(v)); wH(v,30); Print(rH(v)+5);");
        this.programStatementList.add(example7);

        //Ref int v; new(v,20); Ref Ref int a; new(a,v); new(v,30); Print(rH(rH(a)))

        IStatement example8 = makeStatement(
                new VariableDeclaration("v", new RefType(new IntType())),
                new NewStatement("v", new ValueExpression(new IntValue(20))),
                new VariableDeclaration("a", new RefType(new RefType(new IntType()))),
                new NewStatement("a", new VariableExpression("v")),
                new NewStatement("v", new ValueExpression(new IntValue(30))),
                new PrintStatement(new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a"))))
        );
        this.programAsStringList.add("Ref int v; new(v,20); Ref Ref int a; new(a,v); new(v,30); Print(rH(rH(a)))");
        this.programStatementList.add(example8);


        //int v; Ref int a; v=10;new(a,22);
        //  fork(wH(a,30);v=32;print(v);print(rH(a)));
        //print(v);print(rH(a));

        IStatement example9 = makeStatement(
                new VariableDeclaration("v", new IntType()),
                new VariableDeclaration("a", new RefType(new IntType())),
                new AssignStatement("v", new ValueExpression(new IntValue(10))),
                new NewStatement("a", new ValueExpression(new IntValue(22))),

                new ForkStatement(makeStatement(new WriteHeapStatement("a", new ValueExpression(new IntValue(30))),
                        new AssignStatement("v", new ValueExpression(new IntValue(32))),
                        new PrintStatement(new VariableExpression("v")),
                        new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
                )),
                new PrintStatement(new VariableExpression("v")),
                new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
        );
        this.programAsStringList.add("int v; Ref int a; v=10;new(a,22); \n\t\tfork(wH(a,30);v=32;print(v);print(rH(a)));\nprint(v);print(rH(a))");
        this.programStatementList.add(example9);
    }

    public Controller getControllerForChosenExample(int index)
    {
        IStatement example = this.programStatementList.get(index);
        try
        {
            MyIDict<String, IType> typeEnv = new MyDict<>();
            example.typeCheck(typeEnv);

            MyIStack<IStatement> exeStack = new MyStack<>();
            MyIDict<String, IValue> symbolTable = new MyDict<>();
            MyIList<IValue> output = new MyList<>();
            MyIDict<String, BufferedReader> fileTable = new MyDict<>();
            MyIHeap<Integer, IValue> heap = new MyHeap<>();

            ProgramState programState = new ProgramState(exeStack, symbolTable, output, fileTable, heap, example);

            IRepository repository = new Repository("Example" + index + ".txt");
            repository.addProgramState(programState);
            return new Controller(repository);
        }
        catch (Exception e) {
            Alert error = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            error.showAndWait();
            return null;
        }
    }

    public void setProgramExecution(ProgramExecution programExecution)
    {
        this.programExecutionWindow = programExecution;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.populateProgramList();

        this.programListView.setItems(FXCollections.observableArrayList(this.programAsStringList));

        executeButton.setOnAction(
                actionEvent -> {
                    int index = programListView.getSelectionModel().getSelectedIndex();
                    if (index >= 0)
                    {
                        Controller controller = this.getControllerForChosenExample(index);
                        this.programExecutionWindow.setController(controller);
                    }
                    else
                    {
                        Alert error = new Alert(Alert.AlertType.ERROR, "No program was selected!", ButtonType.OK);
                        error.showAndWait();
                    }
                }
        );
    }
}
