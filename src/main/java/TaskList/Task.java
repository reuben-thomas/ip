package TaskList;

import java.io.Serializable;

public abstract class Task implements Serializable {
    private boolean isCompleted;
    private final String taskName;

    public Task(String taskName) {
        this.isCompleted = false;
        this.taskName = taskName;
    }

    public boolean isCompleted() {
        return this.isCompleted;
    }

    public String getTaskName() {
        return this.taskName;
    }

    public void setComplete() {
        this.isCompleted = true;
    }

    public void setIncomplete() {
        this.isCompleted = false;
    }

    private String getCompletedSymbol() {
        return this.isCompleted ? "x" : " ";
    }

    abstract String getAdditionalInfo();

    abstract char getTypeSymbol();

    @Override
    public final String toString() {
        String string = String.format("[%s][%s] %s", this.getTypeSymbol(), this.getCompletedSymbol(), this.getTaskName());
        if (!this.getAdditionalInfo().isEmpty()) {
            string += String.format(" (%s)", this.getAdditionalInfo());
        }
        return string;
    }
}
