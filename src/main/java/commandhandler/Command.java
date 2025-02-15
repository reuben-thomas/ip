package commandhandler;

/**
 * Represents a command that can be executed by the user.
 */
public final class Command {
    /**
     * String typed by user that will uniquely identify this command.
     */
    private final String command;
    /**
     * Example usage containing both the command and sample arguments.
     */
    private final String exampleUsage;
    /**
     * A brief description of what the command will do.
     */
    private final String description;
    /**
     * The function that will be called to generate a response when the command is requested.
     */
    private final CommandFunction commandFunction;

    /**
     * Constructor to create a new command without arguments.
     *
     * @param command         The command that will uniquely identify this command.
     * @param description     A brief description of what the command will do.
     * @param commandFunction The function that will be called when the command is
     *                        executed.
     */
    private Command(String command, String description, CommandFunction commandFunction) {
        this.command = command;
        this.exampleUsage = this.command;
        this.description = description;
        this.commandFunction = commandFunction;
    }

    /**
     * Constructor to create a new command with arguments.
     *
     * @param command         The command that will uniquely identify this command.
     * @param exampleArgs     Example arguments that the command can take.
     * @param description     A brief description of what the command will do.
     * @param commandFunction The function that will be called when the command is
     *                        executed.
     */
    private Command(String command, String exampleArgs, String description, CommandFunction commandFunction) {
        this.command = command;
        this.exampleUsage = this.command + " " + exampleArgs;
        this.description = description;
        this.commandFunction = commandFunction;
    }

    /**
     * Returns a newly created command object without arguments.
     *
     * @param command         The command that will uniquely identify this command.
     * @param description     A brief description of what the command will do.
     * @param commandFunction The function that will be called when the command is
     *                        executed.
     * @return The new command object.
     */
    public static Command createCommandWithoutArgs(String command, String description,
                                                   CommandFunction commandFunction) {
        return new Command(command, description, commandFunction);
    }

    /**
     * Returns a newly created command object with arguments.
     *
     * @param command         The command that will uniquely identify this command.
     * @param exampleArgs     Example arguments that the command can take.
     * @param description     A brief description of what the command will do.
     * @param commandFunction The function that will be called when the command is
     *                        executed.
     * @return The new command object.
     */
    public static Command createCommandWithArgs(String command, String exampleArgs,
                                                String description, CommandFunction commandFunction) {
        return new Command(command, exampleArgs, description, commandFunction);
    }

    /**
     * Returns the command string used to identify the command.
     *
     * @return The command string.
     */
    public String getCommand() {
        return this.command;
    }

    /**
     * Returns the description of what the command will do.
     *
     * @return The description of the command.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns a full example usage of the command, including the command and example arguments.
     *
     * @return The example usage of the command.
     */
    public String getExampleUsage() {
        return this.exampleUsage;
    }

    /**
     * Returns the response from executing the command function with the given arguments.
     *
     * @param commandArgs The arguments to be passed to the commands.
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

        assert result.isSuccess()
                && result.response().isPresent()
                && !result.response().get().isEmpty() : "If successful, response should be present and non-empty";

        return result.response().get();
    }
}
