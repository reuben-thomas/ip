package tasklist;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a deadline task in the task list that has a deadline by when it should be completed.
 */
public class DeadlineTask extends Task {
    private final LocalDate deadlineDate;

    /**
     * Constructor for DeadlineTask class.
     *
     * @param taskName     The name of the deadline task.
     * @param deadlineDate The deadline date of the deadline task.
     */
    public DeadlineTask(String taskName, LocalDate deadlineDate) {
        super(taskName);
        this.deadlineDate = deadlineDate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public char getTypeSymbol() {
        return 'D';
    }

    /**
     * Returns neatly formatted deadline date of the deadline task.
     *
     * @return Formatted deadline information about the deadline task.
     */
    @Override
    public String getAdditionalInfo() {
        return String.format("by: %s", this.deadlineDate.format(DateTimeFormatter.ofPattern("MMM d yyyy")));
    }

    /**
     * Checks if the deadline task is equal to another object.
     *
     * @param obj The object to compare with.
     * @return True if task name and deadline date are the same, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        DeadlineTask other = (DeadlineTask) obj;
        return this.getTaskName().equals(other.getTaskName())
                && this.deadlineDate.equals(other.deadlineDate);
    }
}
