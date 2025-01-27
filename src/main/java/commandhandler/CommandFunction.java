package commandhandler;

@FunctionalInterface
public interface CommandFunction {
    CommandResult execute(String args);
}
