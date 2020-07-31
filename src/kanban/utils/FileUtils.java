package kanban.utils;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import kanban.controllers.ListViewController;
import kanban.models.Task;
import java.io.*;
import java.util.List;

public class FileUtils {
    public static void saveTaskListsToFile(List<ListViewController<Task>> allListViewControllers)
    {
        File selectedFile = chooseFile("Save to:", "Serialized Object File", "*.ser", true);
        if(selectedFile == null)
        {
            return;
        }

        try
        {
            FileOutputStream fos = new FileOutputStream(selectedFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for (ListViewController<Task> listViewController: allListViewControllers)
            {
                for (Task item: listViewController.getListItems()){ oos.writeObject(item);}
                oos.writeObject(null);
            }
            oos.close();
            fos.close();
        }
        catch(FileNotFoundException e)
        {
            showException("FileNotFoundException", e.getMessage());
        }
        catch (IOException e)
        {
            showException("IOException", e.getMessage());
        }
    }

    public static void openTaskListsToFromFile(List<ListViewController<Task>> allListViewControllers)
    {
        File selectedFile = chooseFile("Open from:", "Serialized Object File", "*.ser", false);
        if(selectedFile == null)
        {
            return;
        }

        int currentListIndex = 0;

        try
        {
            FileInputStream fis = new FileInputStream(selectedFile);
            ObjectInputStream ois = new ObjectInputStream(fis);

            for (ListViewController<Task> listViewController: allListViewControllers) { listViewController.clearList(); }

            while(fis.available() != 0)
            {
                Task task = (Task) ois.readObject();
                if(task != null) { allListViewControllers.get(currentListIndex).addItem(task); }
                else { currentListIndex++; }
            }
            ois.close();
            fis.close();
        }
        catch(FileNotFoundException e)
        {
            showException("FileNotFoundException", e.getMessage());
        }
        catch (ClassNotFoundException e)
        {
            showException("ClassNotFoundException", e.getMessage());
        }
        catch (IOException e)
        {
            showException("IOException", e.getMessage());
        }
    }

    public static void exportTaskListToCsvFile(ListViewController<Task> listViewController)
    {
        File selectedFile = chooseFile("Export to:", "Comma-separated Values File", "*.csv", true);
        if(selectedFile == null)
        {
            return;
        }

        try
        {
            FileWriter fw = new FileWriter(selectedFile);
            List<Task> listItems = listViewController.getListItems();
            for (Task listItem : listItems) { fw.append(listItem.toCSV()); }
            fw.flush();
            fw.close();
        }
        catch(IOException e)
        {
            showException("IOException", e.getMessage());
        }
    }

    public static void importTaskListFromCsvFile(ListViewController<Task> listViewController)
    {
        File selectedFile = chooseFile("Import from:", "Comma-separated Values File", "*.csv", false);
        if(selectedFile == null)
        {
            return;
        }

        String line;
        String csvTask = "";
        int semicolonsCount = 0;

        try
        {
            FileReader fr = new FileReader(selectedFile);
            BufferedReader br = new BufferedReader(fr);
            listViewController.clearList();
            while ((line = br.readLine()) != null)
            {
                if(csvTask.length() > 0) { csvTask = csvTask.concat("\n"); }
                csvTask = csvTask.concat(line);

                semicolonsCount += line.chars().filter(ch -> ch == ';').count();
                if(semicolonsCount == 4)
                {
                    listViewController.addItem(new Task(csvTask));
                    csvTask = "";
                    semicolonsCount = 0;
                }
            }
            br.close();
            fr.close();
        }
        catch(IOException e)
        {
            showException("IOException", e.getMessage());
        }
    }

    private static File chooseFile(String title, String fileExtFilterText, String fileExt, boolean showSaveDialog)
    {
        File selectedFile;
        String currentDir = System.getProperty("user.home")+"\\Desktop";
        selectedFile = new File(currentDir);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.setInitialDirectory(selectedFile);
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(fileExtFilterText, fileExt));

        if(showSaveDialog)
        {
            selectedFile = fileChooser.showSaveDialog(null);
        }
        else
        {
            selectedFile = fileChooser.showOpenDialog(null);
        }
        return selectedFile;
    }

    private static void showException(String title, String content)
    {
        Alert infoAlert = new Alert(Alert.AlertType.ERROR);
        infoAlert.setTitle(title);
        infoAlert.setHeaderText(title);
        infoAlert.setContentText(content);
        infoAlert.showAndWait();
    }

}
