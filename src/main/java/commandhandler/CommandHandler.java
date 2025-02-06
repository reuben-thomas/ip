package commandhandler;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Handles a set of commands and assigns each command to a function that will be executed when the command is called.
 */
public class CommandHandler {
    private static final String UNRECOGNIZED_COMMAND_MESSAGE = "I don't recognize that command.";
    private final Map<String, Command> commandHandlerMap;

    /**
     * Constructor for CommandHandler class.
     */
    public CommandHandler() {
        this(true);
    }

    /**
     * Constructor for CommandHandler class.
     *
     * @param addHelpCommand Whether to automatically add a help command.
     */
    public CommandHandler(boolean addHelpCommand) {
        this.commandHandlerMap = new LinkedHashMap<>();
        if (addHelpCommand) {
            this.addCommand(new Command("help", "", this::helpCommandFunction));
        }
    }

    /**
     * Adds a command to the command handler.
     *
     * @param command The command to be added.
     */
    public void addCommand(Command command) {
        this.commandHandlerMap.put(command.getCommand(), command);
    }

    private CommandResult helpCommandFunction(String commandArgs) {
        StringBuilder helpMessage = new StringBuilder("These are the available commands:\n");
        for (Map.Entry<String, Command> commandEntry : this.commandHandlerMap.entrySet()) {
            helpMessage.append(String.format("- %s: %s [e.g. %s]\n",
                    commandEntry.getKey(),
                    commandEntry.getValue().getDescription(),
                    commandEntry.getValue().getExampleUsage()));
        }
        helpMessage.setLength(helpMessage.length() - 1);
        return CommandResult.success(helpMessage.toString());
    }

    /**
     * Gets the response from the appropriate command handler function given an input.
     *
     * @param input The full input, including the command and arguments.
     * @return The response from the command after executing the handler function.
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
