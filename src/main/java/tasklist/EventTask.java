package tasklist;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents an event task in the task list that has a start date and an end date.
 */
public class EventTask extends Task {
    private final LocalDate startDate;
    private final LocalDate endDate;

    /**
     * Constructor for EventTask class.
     *
     * @param taskName  The name of the event task.
     * @param startDate The start date of the event task.
     * @param endDate   The end date of the event task.
     */
    public EventTask(String taskName, LocalDate startDate, LocalDate endDate) {
        super(taskName);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public char getTypeSymbol() {
        return 'E';
    }


    /**
     * Returns neatly formatted start date and end date of the event task.
     *
     * @return Formatted start date and end date information about the event task.
     */
    @Override
    public String getAdditionalInfo() {
        return String.format("from: %s to: %s",
                this.startDate.format(DateTimeFormatter.ofPattern("MMM d yyyy")),
                this.endDate.format(DateTimeFormatter.ofPattern("MMM d yyyy"))
        );
    }

    /**
     * Checks if the event task is equal to another object.
     *
     * @param obj The object to compare with.
     * @return True if task name, start date and end date are the same, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        EventTask other = (EventTask) obj;
        return this.getTaskName().equals(other.getTaskName())
                && this.startDate.equals(other.startDate)
                && this.endDate.equals(other.endDate);
    }
}
