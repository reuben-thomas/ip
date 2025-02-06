package commandhandler;

/**
 * Represents a command that can be executed by the user.
 */
@FunctionalInterface
public interface CommandFunction {
    CommandResult execute(String args);
}
