package TaskList;

public class ToDoTask extends Task {

    public ToDoTask(String taskName) {
        super(taskName);
    }

    @Override
    public char getTypeSymbol() {
        return 'T';
    }

    @Override
    public String getAdditionalInfo() {
        return "";
    }
}
