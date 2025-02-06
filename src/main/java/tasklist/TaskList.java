package tasklist;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a list of tasks.
 */
public class TaskList implements Serializable {

    private final List<Task> taskList;

    /**
     * Constructor for TaskList class.
     */
    public TaskList() {
        this.taskList = new ArrayList<>();
    }

    public int getLength() {
        return this.taskList.size();
    }

    /**
     * Adds a task to the task list.
     *
     * @param task The task to be added.
     */
    public void addTask(Task task) {
        this.taskList.add(task);
    }

    /**
     * Deletes a task from the task list.
     *
     * @param taskIdx The index of the task to be deleted.
     * @return The deleted task.
     */
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        TaskList other = (TaskList) obj;
        if (this.getLength() != other.getLength()) {
            return false;
        }
        for (int i = 0; i < this.getLength(); i++) {
            if (!this.getTask(i).equals(other.getTask(i))) {
                return false;
            }
        }
        return true;
    }
}
