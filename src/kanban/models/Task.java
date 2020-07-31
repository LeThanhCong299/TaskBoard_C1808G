package kanban.models;

import java.io.Serializable;
import java.time.LocalDate;

public class Task implements Serializable {
    public String title;
    public Priority priority;
    public LocalDate expDate;
    public String description;

    public Task(String title, Priority priority, LocalDate expDate, String description)
    {
        this.title = title;
        this.priority = priority;
        this.expDate = expDate;
        this.description = description;
    }

    public Task(String csvText)
    {
        String[] splittedCsvText = csvText.split(";");
        this.title = unwrapTextFromQuotes(splittedCsvText[0]);
        this.priority = Priority.valueOf(unwrapTextFromQuotes(splittedCsvText[1]).toUpperCase());
        this.expDate = LocalDate.parse(unwrapTextFromQuotes(splittedCsvText[2]));
        this.description = unwrapTextFromQuotes(splittedCsvText[3]);
    }

    @Override
    public String toString()
    {
        return title;
    }

    public String toCSV() {
        return this.wrapTextInQuotes(title) + ";"
             + this.wrapTextInQuotes(priority.toString()) + ";"
             + this.wrapTextInQuotes(expDate.toString()) + ";"
             + this.wrapTextInQuotes(description) + ";\n";
    }

    private String wrapTextInQuotes(String text){
        if (text.contains("\"")) {
            text = text.replaceAll("\"", "\"\"");
        }
        return "\"" + text + "\"";
    }

    private String unwrapTextFromQuotes(String text){
        if (text.contains("\"\"")) {
            text = text.replaceAll("\"\"", "\"");
        }
        return text.substring(1, text.length()-1);
    }
}
