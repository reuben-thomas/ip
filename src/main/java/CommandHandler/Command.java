package CommandHandler;

public final class Command {
    private final String command;
    private final String exampleUsage;
    private final String description;
    private final CommandFunction handlerFunction;

    public Command(String command, String description, CommandFunction handlerFunction) {
        this.command = command;
        this.exampleUsage = this.command;
        this.description = description;
        this.handlerFunction = handlerFunction;
    }

    public Command(String command, String exampleArgs, String description, CommandFunction handlerFunction) {
        this.command = command;
        this.exampleUsage = this.command + " " + exampleArgs;
        this.description = description;
        this.handlerFunction = handlerFunction;
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

    public String getResponse(String commandArgs) {
        CommandResult result = this.handlerFunction.execute(commandArgs);

        if (result.resultType() == CommandResult.ResultType.SUCCESS && result.response().isPresent()) {
            return result.response().get();
        } else if (result.resultType() == CommandResult.ResultType.INVALID_USAGE_ERROR) {
            return result.errorMessage().orElseGet(() -> "Invalid usage") + "\nExample: " + this.exampleUsage;
        } else {
            return result.errorMessage().orElseGet(() -> "An unexpected error occurred");
        }
    }
}
