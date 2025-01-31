package commandhandler;

/**
 * Represents a command that can be executed by the user.
 */
public final class Command {
    private final String command;
    private final String exampleUsage;
    private final String description;
    private final CommandFunction commandFunction;

    /**
     * Constructor for Command class.
     *
     * @param command         The command that will uniquely identify this command.
     * @param description     A brief description of what the command will do.
     * @param commandFunction The function that will be called when the command is executed.
     */
    public Command(String command, String description, CommandFunction commandFunction) {
        this.command = command;
        this.exampleUsage = this.command;
        this.description = description;
        this.commandFunction = commandFunction;
    }

    /**
     * Constructor for Command class.
     *
     * @param command         The command that will uniquely identify this command.
     * @param exampleArgs     Example arguments that the command can take.
     * @param description     A brief description of what the command will do.
     * @param commandFunction The function that will be called when the command is executed.
     */
    public Command(String command, String exampleArgs, String description, CommandFunction commandFunction) {
        this.command = command;
        this.exampleUsage = this.command + " " + exampleArgs;
        this.description = description;
        this.commandFunction = commandFunction;
    }

    public String getCommand() {
        return this.command;
    }

    public String getDescription() {
        return this.description;
    }

    public String getExampleUsage() {
        return this.exampleUsage;
    }

    /**
     * Executes the command function with the given arguments.
     *
     * @param commandArgs The arguments to be passed to the command.
     * @return The response from the command after executing the handler function.
     */
    public String getResponse(String commandArgs) {
        CommandResult result = this.commandFunction.execute(commandArgs);

        if (result.resultType() == CommandResult.ResultType.SUCCESS && result.response().isPresent()) {
            return result.response().get();
        } else if (result.resultType() == CommandResult.ResultType.INVALID_USAGE_ERROR) {
            return result.errorMessage().orElseGet(() -> "Invalid usage") + "\nExample: " + this.exampleUsage;
        } else {
            return result.errorMessage().orElseGet(() -> "An unexpected error occurred");
        }
    }
}
