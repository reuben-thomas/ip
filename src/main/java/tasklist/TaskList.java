package tasklist;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TaskList implements Serializable {

    private final List<Task> taskList;

    public TaskList() {
        this.taskList = new ArrayList<Task>();
    }

    public int getLength() {
        return this.taskList.size();
    }

    public void addTask(Task task) {
        this.taskList.add(task);
    }

    public Task deleteTask(int taskIdx) {
        Task deletedTask = this.taskList.get(taskIdx);
        this.taskList.remove(taskIdx);
        return deletedTask;
    }

    public Task getTask(int taskIdx) {
        return this.taskList.get(taskIdx);
    }

    public void setTaskComplete(int taskIdx) {
        this.taskList.get(taskIdx).setComplete();
    }

    public void setTaskIncomplete(int taskIdx) {
        this.taskList.get(taskIdx).setIncomplete();
    }

    @Override
    public String toString() {
        if (this.taskList.isEmpty()) {
            return "No tasks to display.";
        }

        StringBuilder str = new StringBuilder();
        int taskNumber = 1;
        for (Task task : this.taskList) {
            str.append(String.format("%d. %s\n", taskNumber, task.toString()));
            taskNumber++;
        }
        str.setLength(str.length() - 1);
        return str.toString();
    }
}
