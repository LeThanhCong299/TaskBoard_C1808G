package kanban.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import kanban.models.Priority;
import kanban.models.Task;
import kanban.models.TaskFormMode;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class TaskFormController implements Initializable
{
    @FXML private TextField titleTextField;
    @FXML private ChoiceBox<Priority> priorityChoiceBox;
    @FXML private DatePicker expDateDatePicker;
    @FXML private TextArea descriptionTextArea;
    @FXML private Button addTaskButton;
    private TaskFormMode taskFormMode;
    private ListViewController<Task> targetListViewController;
    private Stage stage;
    private Task task;

    public TaskFormController(TaskFormMode taskFormMode, ListViewController<Task> targetListViewController)
    {
        this.taskFormMode = taskFormMode;
        this.targetListViewController = targetListViewController;
    }

    public TaskFormController(TaskFormMode taskFormMode, ListViewController<Task> targetListViewController, Task task)
    {
        this(taskFormMode, targetListViewController);
        this.task = task;
    }

    public void displayStage()
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/taskForm.fxml"));
            fxmlLoader.setController(this);
            Parent taskFormView = fxmlLoader.load();
            this.stage = new Stage();
            stage.setTitle(taskFormMode.getFormTitle());
            stage.setScene(new Scene(taskFormView));
            stage.setMinWidth(600);
            stage.setMinHeight(400);
            stage.show();
        }
        catch(IOException ex) { System.err.println(ex.toString()); }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        addTaskButton.setText(taskFormMode.getFormTitle());
        priorityChoiceBox.getItems().addAll(Priority.values());
        addTaskButton.setOnMouseClicked((MouseEvent mouseEvent) -> this.addNewTaskToList());

        if(task != null)
        {
            populateFormWithTaskData(task);
        }
    }

    private void populateFormWithTaskData(Task task){
        titleTextField.setText(task.title);
        priorityChoiceBox.setValue(task.priority);
        expDateDatePicker.setValue(task.expDate);
        descriptionTextArea.setText(task.description);
    }

    private void addNewTaskToList()
    {
        String title = titleTextField.getCharacters().toString();
        Priority priority = priorityChoiceBox.getValue();
        LocalDate expDate = expDateDatePicker.getValue();
        String description = descriptionTextArea.getParagraphs().stream().collect(Collectors.joining("\n"));

        if(title.length() > 0 && priority != null && expDate != null)
        {
            if(this.taskFormMode == TaskFormMode.ADD){
                this.targetListViewController.addItem(new Task(title, priority, expDate, description));
            }
            if(this.taskFormMode == TaskFormMode.EDIT) {
                this.task.title = title;
                this.task.priority = priority;
                this.task.expDate = expDate;
                this.task.description = description;
                this.targetListViewController.resetListViewItems();
            }
            this.stage.close();
        }
    }
}
