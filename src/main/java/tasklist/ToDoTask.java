package tasklist;

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
