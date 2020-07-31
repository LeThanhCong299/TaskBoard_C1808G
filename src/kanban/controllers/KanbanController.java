package kanban.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.input.*;
import kanban.models.Priority;
import kanban.models.Task;
import kanban.models.TaskFormMode;
import kanban.utils.FileUtils;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class KanbanController implements Initializable
{
    @FXML private Button addTaskButton;
    @FXML private ListView toDoListView;
    @FXML private ListView inPrListView;
    @FXML private ListView doneListView;
    @FXML private MenuItem saveMenuItem;
    @FXML private MenuItem openMenuItem;
    @FXML private MenuItem exportToDoMenuItem;
    @FXML private MenuItem exportInPrMenuItem;
    @FXML private MenuItem exportDoneMenuItem;
    @FXML private MenuItem importToDoMenuItem;
    @FXML private MenuItem importInPrMenuItem;
    @FXML private MenuItem importDoneMenuItem;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        ListViewController<Task> toDoListViewController = new ListViewController<>(toDoListView);
        ListViewController<Task> inPrListViewController = new ListViewController<>(inPrListView);
        ListViewController<Task> doneListViewController = new ListViewController<>(doneListView);
        this.addSampleDataToListViews(toDoListViewController, inPrListViewController, doneListViewController);

        ArrayList<ListViewController<Task>> allListViewControllers = new ArrayList<>(Arrays.asList(toDoListViewController, inPrListViewController, doneListViewController));

        addTaskButton.setOnMouseClicked((MouseEvent mouseEvent) -> displayAddForm(toDoListViewController));
        saveMenuItem.setOnAction((ActionEvent event) -> FileUtils.saveTaskListsToFile(allListViewControllers));
        openMenuItem.setOnAction((ActionEvent event) -> FileUtils.openTaskListsToFromFile(allListViewControllers));
        exportToDoMenuItem.setOnAction((ActionEvent event) -> FileUtils.exportTaskListToCsvFile(toDoListViewController));
        exportInPrMenuItem.setOnAction((ActionEvent event) -> FileUtils.exportTaskListToCsvFile(inPrListViewController));
        exportDoneMenuItem.setOnAction((ActionEvent event) -> FileUtils.exportTaskListToCsvFile(doneListViewController));
        importToDoMenuItem.setOnAction((ActionEvent event) -> FileUtils.importTaskListFromCsvFile(toDoListViewController));
        importInPrMenuItem.setOnAction((ActionEvent event) -> FileUtils.importTaskListFromCsvFile(inPrListViewController));
        importDoneMenuItem.setOnAction((ActionEvent event) -> FileUtils.importTaskListFromCsvFile(doneListViewController));
    }

    private void displayAddForm(ListViewController<Task> targetListViewController)
    {
        TaskFormController taskFromController = new TaskFormController(TaskFormMode.ADD, targetListViewController);
        taskFromController.displayStage();
    }

    private void addSampleDataToListViews(
            ListViewController<Task> toDoListViewController,
            ListViewController<Task> inPrListViewController,
            ListViewController<Task> doneListViewController)
    {
        toDoListViewController.addItems(Arrays.asList(
                new Task("Title", Priority.MEDIUM, LocalDate.of(2018,8,22), "line1\nline2"),
                new Task("Title2", Priority.HIGH, LocalDate.of(2018,3,3), "line1\nline2")
        ));
        inPrListViewController.addItems(Arrays.asList(
                new Task("Title3", Priority.LOW, LocalDate.of(2018,3,3), "line1\nline2"),
                new Task("Title4", Priority.MEDIUM, LocalDate.of(2018,3,3), "line1\nline2")
        ));
        doneListViewController.addItem(
                new Task("Title5", Priority.HIGH, LocalDate.of(2018,3,3), "line1\nline2"
        ));
    }
}
