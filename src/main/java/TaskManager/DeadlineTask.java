package TaskManager;

public class DeadlineTask extends Task {
    private String deadline;

    public DeadlineTask(String taskName, String deadline) {
        super(taskName);
        this.deadline = deadline;
    }

    @Override
    public char getTypeSymbol() {
        return 'D';
    }

    @Override
    public String getAdditionalInfo() {
        return String.format("by: %s", this.deadline);
    }
}
