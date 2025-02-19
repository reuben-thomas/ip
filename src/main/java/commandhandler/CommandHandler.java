package commandhandler;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Manages a set of commands and executes the appropriate handler function with required arguments based on user input.
 */
public class CommandHandler {
    /**
     * The message to be displayed in response to an unrecognized command.
     */
    private static final String UNRECOGNIZED_COMMAND_MESSAGE = "I don't recognize that command.";
    /**
     * The map of command names to their respective command objects.
     */
    private final Map<String, Command> commandHandlerMap;

    /**
     * Constructor for CommandHandler class.
     *
     * @param addHelpCommand Whether to automatically add a help command.
     */
    private CommandHandler(boolean addHelpCommand) {
        this.commandHandlerMap = new LinkedHashMap<>();

        if (addHelpCommand) {
            this.addHelpCommandHandler();
        }
    }

    /**
     * Returns a new command handler with an optional help command.
     *
     * @param addHelpCommand Whether to add an automatically generated help command.
     * @return The new command handler.
     */
    public static CommandHandler createCommandHandler(boolean addHelpCommand) {
        return new CommandHandler(addHelpCommand);
    }

    /**
     * Adds a command to the command handler.
     * The command must not already exist in the command handler.
     *
     * @param command The command to be added.
     */
    public void addCommand(Command command) {
        assert this.commandHandlerMap.containsKey(command.getCommand())
                : "Command identified by the same name already exists."
                + "Please ensure all command names aee unique.";

        this.commandHandlerMap.put(command.getCommand(), command);
    }

    /**
     * Adds a help command to the command handler.
     */
    private void addHelpCommandHandler() {
        this.addCommand(Command.createCommandWithoutArgs(
                "help",
                "Displays the available commands and their descriptions.",
                this::helpCommandFunction));
    }

    /**
     * Returns a help message with all available commands, example usages, and their descriptions.
     *
     * @param commandArgs The arguments passed to the help command.
     * @return help message result
     */
    private CommandResult helpCommandFunction(String commandArgs) {
        StringBuilder helpMessage = new StringBuilder("These are the available commands:\n");

        for (Map.Entry<String, Command> commandEntry : this.commandHandlerMap.entrySet()) {
            helpMessage.append(String.format("- %s: %s [e.g. %s]\n",
                    commandEntry.getKey(),
                    commandEntry.getValue().getDescription(),
                    commandEntry.getValue().getExampleUsage()));
        }

        helpMessage.setLength(helpMessage.length() - 1);
        return CommandResult.createSuccessResult(helpMessage.toString());
    }

    /**
     * Returns the response from the appropriate command handler function given an input.
     *
     * @param input The full input, including the command and arguments.
     * @return The response generated by the command handler function.
     */
    public String getResponse(String input) {
        String[] inputSplit = input.split(" ", 2);
        String commandName = inputSplit[0];
        String commandArgs = inputSplit.length > 1 ? inputSplit[1] : "";

        Command command = this.commandHandlerMap.get(commandName);
        if (command == null) {
            return CommandHandler.UNRECOGNIZED_COMMAND_MESSAGE;
        }

        return command.getResponse(commandArgs);
    }
}
