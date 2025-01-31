package tasklist;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EventTask extends Task {
    private final LocalDate startDate;
    private final LocalDate endDate;

    public EventTask(String taskName, LocalDate startDate, LocalDate endDate) {
        super(taskName);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public char getTypeSymbol() {
        return 'E';
    }

    @Override
    public String getAdditionalInfo() {
        return String.format("from: %s to: %s",
                this.startDate.format(DateTimeFormatter.ofPattern("MMM d yyyy")),
                this.endDate.format(DateTimeFormatter.ofPattern("MMM d yyyy"))
        );
    }

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
