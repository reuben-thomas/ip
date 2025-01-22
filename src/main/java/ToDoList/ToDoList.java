package ToDoList;

import java.util.LinkedHashMap;
import java.util.Map;

public class ToDoList {

    private Map<String, Task> taskList;

    public ToDoList() {
        this.taskList = new LinkedHashMap<String, Task>();
    }

    public void addTask(String taskName) {
        this.taskList.put(taskName, new Task());
    }

    private String taskIndexToName(int taskIdx) {
        return this.taskList.keySet().stream().toList().get(taskIdx);
    }

    public String getTaskDisplayString(int taskIdx) {
        return this.getTaskDisplayString(this.taskIndexToName(taskIdx));
    }

    private String getTaskDisplayString(String taskName) {
        String taskDisplayString = "";
        if (this.taskList.get(taskName).isCompleted()) {
            taskDisplayString += "[x] ";
        } else {
            taskDisplayString += "[ ] ";
        }
        return taskDisplayString += taskName;
    }

    public void markTaskCompleted(int taskIdx) {
        this.taskList.get(this.taskIndexToName(taskIdx)).markCompleted();
    }

    public void markTaskIncomplete(int taskIdx) {
        this.taskList.get(this.taskIndexToName(taskIdx)).markIncomplete();
    }

    public String toDisplayString() {
        if (this.taskList.isEmpty()) {
            return "No tasks to display.";
        }

        StringBuilder str = new StringBuilder();
        int taskNumber = 1;
        for (Map.Entry<String, Task> nameTaskEntry : this.taskList.entrySet()) {
            str.append(taskNumber)
                    .append(". ")
                    .append(this.getTaskDisplayString(nameTaskEntry.getKey()));
            if (taskNumber != this.taskList.size()) {
                str.append("\n");
            }
            taskNumber++;
        }
        return str.toString();
    }
}
