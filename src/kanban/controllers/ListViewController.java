package kanban.controllers;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.input.*;
import kanban.models.Task;
import kanban.models.TaskFormMode;
import kanban.utils.TempStoreUtil;
import java.util.ArrayList;
import java.util.List;

public class ListViewController<T>
{
    private ListView<T> listView;
    private List<T> listItems;

    public ListViewController(ListView listView)
    {
        this.listView = listView;
        this.listItems = new ArrayList<>();
        this.setupListViewDragAndDrop();
        this.setupListViewCells();
    }

    public void clearList()
    {
        this.listItems = new ArrayList<>();
        this.resetListViewItems();
    }

    public void addItem(T item)
    {
        this.listItems.add(item);
        this.resetListViewItems();
    }

    public void addItems(List<T> items)
    {
        this.listItems.addAll(items);
        this.resetListViewItems();
    }

    public void delItem(T item)
    {
        this.listItems.remove(item);
        this.resetListViewItems();
    }

    public void resetListViewItems()
    {
        this.listView.setItems(FXCollections.observableArrayList(listItems));
    }

    public List<T> getListItems()
    {
        return listItems;
    }

    private void setupListViewDragAndDrop()
    {
        listView.setOnDragDetected((MouseEvent mouseEvent) ->
            {
                ClipboardContent clipboardContent = new ClipboardContent();
                Dragboard dragBoard = listView.startDragAndDrop(TransferMode.MOVE);
                Object selectedItem = listView.getSelectionModel().getSelectedItem();

                if (selectedItem != null)
                {
                    clipboardContent.putString(selectedItem.toString());
                    dragBoard.setContent(clipboardContent);
                    TempStoreUtil.getInstance().setItem(selectedItem);
                }
            }
        );

        listView.setOnDragEntered((DragEvent dragEvent) -> listView.setStyle("-fx-control-inner-background: #DEE3DA;"));
        listView.setOnDragExited((DragEvent dragEvent) -> listView.setStyle("-fx-control-inner-background: #FFF;"));
        listView.setOnDragOver((DragEvent dragEvent) -> dragEvent.acceptTransferModes(TransferMode.MOVE));
        listView.setOnDragDropped((DragEvent dragEvent) -> addItem((T) TempStoreUtil.getInstance().getItem())); // executes on target listView
        listView.setOnDragDone((DragEvent dragEvent) -> delItem((T) TempStoreUtil.getInstance().getItem())); // executes on source listView
    }

    private void setupListViewCells() {
        listView.setCellFactory((lv) -> new ListCell<T>()
            {
                @Override
                public void updateItem(T item, boolean empty)
                {
                    super.updateItem(item, empty);
                    if (empty)
                    {
                        textProperty().bind(Bindings.format(""));
                        setContextMenu(null);
                    }
                    else
                    {
                        textProperty().bind(itemProperty().asString());
                        setContextMenu(createItemContextMenu(this));
                        T cellItem = itemProperty().getValue();
                        if (cellItem instanceof Task)
                        {
                            Tooltip.install(this, new Tooltip(((Task) cellItem).description));
                        }
                    }
                }
            }
        );
    }

    private ContextMenu createItemContextMenu(ListCell<T> cell)
    {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem editItem = new MenuItem();
        editItem.textProperty().bind(Bindings.format("Edit \"%s\"", cell.itemProperty()));
        editItem.setOnAction(event ->
        {
            T item = cell.getItem();
            if (item instanceof Task)
            {
                this.displayTaskEditForm((Task)item, (ListViewController<Task>) this);
            }
        });

        MenuItem deleteItem = new MenuItem();
        deleteItem.textProperty().bind(Bindings.format("Delete \"%s\"", cell.itemProperty()));
        deleteItem.setOnAction(event -> delItem(cell.getItem()));

        contextMenu.getItems().addAll(editItem, deleteItem);
        return contextMenu;
    }

    private void displayTaskEditForm(Task item, ListViewController<Task> targetListViewController)
    {
        TaskFormController taskFromController = new TaskFormController(TaskFormMode.EDIT, targetListViewController, item);
        taskFromController.displayStage();
    }
}
