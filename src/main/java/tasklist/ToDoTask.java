package tasklist;

/**
 * Represents a to-do task in the task list that has no deadline or additional information.
 */
public class ToDoTask extends Task {

    /**
     * Constructor for ToDoTask class.
     *
     * @param taskName The name of the to-do task.
     */
    public ToDoTask(String taskName) {
        super(taskName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public char getTypeSymbol() {
        return 'T';
    }

    /**
     * Returns additional information about the to-do task, which is an empty string.
     *
     * @return An empty string.
     */
    @Override
    public String getAdditionalInfo() {
        return "";
    }

    /**
     * Checks if the to-do task is equal to another object.
     *
     * @param obj The object to compare with.
     * @return True if task name is the same, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        ToDoTask other = (ToDoTask) obj;
        return this.getTaskName().equals(other.getTaskName());
    }
}
