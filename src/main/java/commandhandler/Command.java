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
    private Command(String command, String description, CommandFunction commandFunction) {
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
    private Command(String command, String exampleArgs, String description, CommandFunction commandFunction) {
        this.command = command;
        this.exampleUsage = this.command + " " + exampleArgs;
        this.description = description;
        this.commandFunction = commandFunction;
    }

    /**
     * Factory method to create a new command without arguments.
     *
     * @param command         The command that will uniquely identify this command.
     * @param description     A brief description of what the command will do.
     * @param commandFunction The function that will be called when the command is executed.
     */
    public static Command createCommandWithoutArgs(String command, String description,
                                                   CommandFunction commandFunction) {
        return new Command(command, description, commandFunction);
    }

    /**
     * Factory method to create a new command with arguments.
     *
     * @param command         The command that will uniquely identify this command.
     * @param exampleArgs     Example arguments that the command can take.
     * @param description     A brief description of what the command will do.
     * @param commandFunction The function that will be called when the command is executed.
     */
    public static Command createCommandWithArgs(String command, String exampleArgs,
                                                String description, CommandFunction commandFunction) {
        return new Command(command, exampleArgs, description, commandFunction);
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

        if (result.isUnexpectedError()) {
            return result.errorMessage().orElseGet(() -> "An unexpected error occurred");
        }

        if (result.isUsageError()) {
            return result.errorMessage().orElseGet(() -> "Invalid usage") + "\nExample: " + this.exampleUsage;
        }

        return result.response().get();
    }
}
