package kanban;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent kanbanView = FXMLLoader.load(getClass().getResource("views/kanban.fxml"));
        primaryStage.setTitle("Kanban Board");
        primaryStage.setScene(new Scene(kanbanView));
        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(400);

        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
