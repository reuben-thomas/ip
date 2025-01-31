package tasklist;

import java.io.Serializable;

/**
 * Represents a task in the task list.
 */
public abstract class Task implements Serializable {
    private final String taskName;
    private boolean isCompleted;

    /**
     * Constructor for Task class.
     *
     * @param taskName The name of the task.
     */
    public Task(String taskName) {
        this.isCompleted = false;
        this.taskName = taskName;
    }

    public boolean isCompleted() {
        return this.isCompleted;
    }

    public String getTaskName() {
        return this.taskName;
    }

    /**
     * Mark task as complete.
     */
    public void setComplete() {
        this.isCompleted = true;
    }

    /**
     * Mark task as incomplete.
     */
    public void setIncomplete() {
        this.isCompleted = false;
    }

    /**
     * Get a symbol representing the task's current completion.
     *
     * @return A string symbol representing the task's current completion.
     */
    private String getCompletedSymbol() {
        return this.isCompleted ? "x" : " ";
    }

    /**
     * Get additional information about the task.
     *
     * @return A string containing additional information about the task.
     */
    abstract String getAdditionalInfo();

    /**
     * Get a symbol representing the task type.
     *
     * @return A character representing the task type.
     */
    abstract char getTypeSymbol();

    /**
     * Check if the task is equal to another object.
     * A task should be considered equal to another task if they have the same task name and additional fields.
     *
     * @return A string representation of the task.
     */
    @Override
    public abstract boolean equals(Object obj);

    /**
     * Returns a neatly formatted string representation of the task, including the task type, completion status, task name and additional information.
     *
     * @return A string representation of the task.
     */
    @Override
    public final String toString() {
        String string = String.format("[%s][%s] %s", this.getTypeSymbol(), this.getCompletedSymbol(), this.getTaskName());
        if (!this.getAdditionalInfo().isEmpty()) {
            string += String.format(" (%s)", this.getAdditionalInfo());
        }
        return string;
    }
}
