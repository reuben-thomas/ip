package TaskManager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EventTask extends Task {
    private LocalDate startDate;
    private LocalDate endDate;

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
}
