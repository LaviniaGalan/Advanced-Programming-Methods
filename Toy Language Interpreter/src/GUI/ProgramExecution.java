package GUI;

import Controller.Controller;
import Model.ProgramState;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Pair;

import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collector;
import java.util.stream.Collectors;


public class ProgramExecution implements Initializable {
    @FXML
    private TextField programStateID;

    @FXML
    private TableView<Pair<String, String>> heapTable;
    @FXML
    private TableColumn<Pair<String, String>, String> heapTableAddress;
    @FXML
    private TableColumn<Pair<String, String>, String> heapTableValue;

    @FXML
    private ListView<String> outputList;

    @FXML
    private ListView<String> fileTable;

    @FXML
    private ListView<Integer> programStateIDList;

    @FXML
    private ListView<String> exeStack;

    @FXML
    private TableView<Pair<String, String>> symbolTable;
    @FXML
    private TableColumn<Pair<String, String>, String> symbolTableVariableName;
    @FXML
    private TableColumn<Pair<String, String>, String> symbolTableValue;

    @FXML
    private Button runOneStepButton;

    private Controller controller = null;
    public void setController(Controller controller)
    {
        this.controller = controller;

        this.updateFileTable();
        this.updateOutputList();
        this.updateProgramStateID();
        this.updateProgramStateIDList();
    }

    public void updateFileTable()
    {
        this.fileTable.setItems(FXCollections.observableList(this.controller.getFileTable()));
    }

    public void updateOutputList()
    {
        this.outputList.setItems(FXCollections.observableList(this.controller.getOutputList()));
    }

    public void updateProgramStateIDList()
    {
        this.programStateIDList.setItems(FXCollections.observableList(this.controller.getProgramStateIDs()));
    }

    public void updateHeap()
    {
        List<Pair<String, String>> heapAsList = this.controller.getHeap().entrySet().
                                                stream().
                                                map(pair -> new Pair<String, String>(pair.getKey().toString(), pair.getValue().toString())).
                                                collect(Collectors.toList());
        this.heapTable.setItems(FXCollections.observableList(heapAsList));
    }

    public void updateProgramStateID()
    {
        this.programStateID.setText(this.controller.getCurrentProgramStateID().toString());
    }

    public void updateExeStack(ProgramState program)
    {
        this.exeStack.setItems(FXCollections.observableList(program.getExeStack().getContent().stream().map(Objects::toString).collect(Collectors.toList())));
    }

    public void updateSymbolTable(ProgramState program)
    {
        List<Pair<String, String>> symbolTable = program.getSymbolTable().getContent().entrySet().
                stream().
                map(pair -> new Pair<String, String>(pair.getKey().toString(), pair.getValue().toString())).
                collect(Collectors.toList());
        this.symbolTable.setItems(FXCollections.observableList(symbolTable));
    }

    public void executeOneStep()
    {
        if (this.controller.isProgramFinished())
        {
            Alert error = new Alert(Alert.AlertType.ERROR, "The program is finished!", ButtonType.OK);
            error.showAndWait();
            this.programStateIDList.getItems().clear();
            this.outputList.getItems().clear();
            this.fileTable.getItems().clear();
            this.symbolTable.getItems().clear();
            this.heapTable.getItems().clear();
            this.programStateID.setText("");
        }
        else
        {
            try{
                this.controller.executeOneStep();
                this.updateOutputList();
                this.updateFileTable();
                this.updateHeap();
                this.updateProgramStateIDList();

                if (this.controller.getProgramStateIDs().size() <= 1)
                {
                    this.updateExeStack(this.controller.getProgramByIndex(0));
                    this.updateSymbolTable(this.controller.getProgramByIndex(0));
                }
                else
                {
                    this.exeStack.getItems().clear();
                    this.symbolTable.getItems().clear();
                }


            }
            catch (Exception e)
            {
                Alert error = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
                error.showAndWait();
                this.programStateIDList.getItems().clear();
                this.outputList.getItems().clear();
                this.fileTable.getItems().clear();
                this.symbolTable.getItems().clear();
                this.heapTable.getItems().clear();
                this.programStateID.setText("");
            }

        }
    }

    public ProgramState getSelectedProgram()
    {
        int index = this.programStateIDList.getSelectionModel().getSelectedIndex();
        return this.controller.getProgramByIndex(index);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.heapTableAddress.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey()));
        this.heapTableValue.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue()));

        this.symbolTableVariableName.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey()));
        this.symbolTableValue.setCellValueFactory(p-> new SimpleStringProperty(p.getValue().getValue()));

        this.runOneStepButton.setOnAction(
                actionEvent -> {
                    if(this.controller == null)
                    {
                        Alert error = new Alert(Alert.AlertType.ERROR, "No program was selected!", ButtonType.OK);
                        error.showAndWait();
                    }
                    this.executeOneStep();
                }
        );


        this.programStateIDList.setOnMouseClicked(
                actionEvent -> {
                    int index = this.programStateIDList.getSelectionModel().getSelectedIndex();
                    if (index >= 0) {
                        ProgramState program = this.controller.getProgramByIndex(index);
                        this.updateSymbolTable(program);
                        this.updateExeStack(program);

                        this.programStateID.setText(program.getProgramID().toString());
                    }
                }
        );
    }
}
