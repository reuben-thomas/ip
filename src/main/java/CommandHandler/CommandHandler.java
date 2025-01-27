package CommandHandler;

import java.util.LinkedHashMap;
import java.util.Map;

public class CommandHandler {
    private static final String UNRECOGNIZED_COMMAND_MESSAGE = "I don't recognize that command.";
    private final Map<String, Command> commandHandlerMap;

    public CommandHandler() {
        this(true);
    }

    public CommandHandler(boolean addHelpCommand) {
        this.commandHandlerMap = new LinkedHashMap<>();
        if (addHelpCommand) {
            this.addCommand(new Command("help", "", this::helpCommandFunction));
        }
    }

    public void addCommand(Command command) {
        this.commandHandlerMap.put(command.getCommand(), command);
    }

    private CommandResult helpCommandFunction(String commandArgs) {
        StringBuilder helpMessage = new StringBuilder("These are the available commands:\n");
        for (Map.Entry<String, Command> commandEntry : this.commandHandlerMap.entrySet()) {
            helpMessage.append(String.format("- %s: %s [e.g. %s]\n", commandEntry.getKey(), commandEntry.getValue().getDescription(), commandEntry.getValue().getExampleUsage()));
        }
        helpMessage.setLength(helpMessage.length() - 1);
        return CommandResult.success(helpMessage.toString());
    }

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
