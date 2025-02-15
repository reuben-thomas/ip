package commandhandler;

/**
 * A function associated with a command to be executed and generate a response when the command is requested.
 */
@FunctionalInterface
public interface CommandFunction {
    CommandResult execute(String args);
}
