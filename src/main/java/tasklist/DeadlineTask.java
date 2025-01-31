package tasklist;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DeadlineTask extends Task {
    private final LocalDate deadlineDate;

    public DeadlineTask(String taskName, LocalDate deadlineDate) {
        super(taskName);
        this.deadlineDate = deadlineDate;
    }

    @Override
    public char getTypeSymbol() {
        return 'D';
    }

    @Override
    public String getAdditionalInfo() {
        return String.format("by: %s", this.deadlineDate.format(DateTimeFormatter.ofPattern("MMM d yyyy")));
    }

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
