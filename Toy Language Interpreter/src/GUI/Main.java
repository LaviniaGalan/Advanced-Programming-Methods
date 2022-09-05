package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader programExecution = new FXMLLoader();
        programExecution.setLocation(getClass().getResource("Execution.fxml"));
        Parent programExecutionWindow = programExecution.load();

        FXMLLoader programSelection = new FXMLLoader();
        programSelection.setLocation(getClass().getResource("Selection.fxml"));
        Parent programSelectionWindow = programSelection.load();

        ProgramExecution execution = programExecution.getController();
        ProgramSelection selection = programSelection.getController();
        selection.setProgramExecution(execution);

        primaryStage.setTitle("Program Execution");
        primaryStage.setScene(new Scene(programExecutionWindow, 900, 600));
        primaryStage.show();

        Stage secondStage = new Stage();
        secondStage.setTitle("Program Selection");
        secondStage.setScene(new Scene(programSelectionWindow, 700, 400));
        secondStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
