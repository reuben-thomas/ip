package CommandHandler;

@FunctionalInterface
public interface CommandFunction {
    CommandResult execute(String args);
}
