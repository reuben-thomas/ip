package ToDoList;

import java.util.Date;

public class EventTask extends Task {
    private String startDate;
    private String endDate;

    public EventTask(String taskName, String startDate, String endDate) {
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
        return String.format("from: %s to: %s", this.startDate, this.endDate);
    }
}
