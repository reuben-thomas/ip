package Kipp;

import ToDoList.*;

import javax.swing.text.html.Option;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class Kipp {
    private static final String LOGO = """
            ██   ██ ██ ██████  ██████
            ██  ██  ██ ██   ██ ██   ██
            █████   ██ ██████  ██████
            ██  ██  ██ ██      ██
            ██   ██ ██ ██      ██
            """;
    private static final String NAME = "KIPP";
    private static final String UNRECOGNIZED_COMMAND_MESSAGE = "I don't recognize that command.";

    private Map<String, CommandHandler> commandHandlerMap;
    private ToDoList toDoList;

    public Kipp() {
        this.toDoList = new ToDoList();
        this.initializeCommandHandlerMap();
    }

    private void initializeCommandHandlerMap() {
        this.commandHandlerMap = new LinkedHashMap<>();
        this.commandHandlerMap.put("help", new CommandHandler("help", "",
                this::helpCommandHandler));
        this.commandHandlerMap.put("hello", new CommandHandler("hello", "",
                args -> CommandHandler.Result.success(Kipp.getSelfIntroduction())));
        this.commandHandlerMap.put("bye", new CommandHandler("bye", "",
                args -> CommandHandler.Result.success(Kipp.getSignOut())));
        this.commandHandlerMap.put("list", new CommandHandler("list", "",
                this::listCommandHandler));
        this.commandHandlerMap.put("mark", new CommandHandler("mark", "<task number>",
                this::setCompleteCommandHandler));
        this.commandHandlerMap.put("unmark", new CommandHandler("unmark", "<task number>",
                this::setIncompleteCommandHandler));
        this.commandHandlerMap.put("todo", new CommandHandler("todo", "<task description>",
                this::addTodoCommandHandler));
        this.commandHandlerMap.put("deadline", new CommandHandler("deadline",
                "<task description> /by <deadline>",
                this::addDeadlineCommandHandler));
        this.commandHandlerMap.put("event", new CommandHandler("event",
                "<task description> /from <start time> /to <end time>",
                this::addEventCommandHandler));
        this.commandHandlerMap.put("delete", new CommandHandler("delete", "<task number>",
                this::deleteTaskCommandHandler));
    }

    public static String getName() {
        return Kipp.NAME;
    }

    public static String getLogo() {
        return Kipp.LOGO;
    }

    public static String getSelfIntroduction() {
        return "Hi there, this is " + Kipp.NAME + ".\nHow can I help?";
    }

    public static String getSignOut() {
        return "Goodbye. Safe travels.";
    }

    public String getResponse(String input) {
        CommandHandler commandHandler = this.commandHandlerMap.get(input.split(" ", 2)[0]);
        if (commandHandler == null) {
            return Kipp.UNRECOGNIZED_COMMAND_MESSAGE;
        } else {
            return commandHandler.getResponse(input);
        }
    }

    private CommandHandler.Result listCommandHandler(String args) {
        return this.toDoList.getLength() == 0
                ? CommandHandler.Result.success("You have 0 tasks on your list.")
                : CommandHandler.Result.success(this.toDoList.toString());
    }

    private CommandHandler.Result setCompleteCommandHandler(String args) {
        return this.setCompletionCommandHandlerHelper(args, true);
    }

    private CommandHandler.Result setIncompleteCommandHandler(String args) {
        return this.setCompletionCommandHandlerHelper(args, false);
    }

    private CommandHandler.Result setCompletionCommandHandlerHelper(String args, boolean isComplete) {
        if (this.toDoList.getLength() == 0) {
            return CommandHandler.Result.error("You have no tasks to delete.");
        }

        Optional<Integer> taskIdxOpt;
        taskIdxOpt = this.validateTaskIndex(args);
        if (taskIdxOpt.isEmpty()) {
            return CommandHandler.Result.error(getInvalidTaskIndexMessage());
        }
        int taskIdx = taskIdxOpt.get();

        if (isComplete == this.toDoList.getTask(taskIdx).isCompleted()) {
            return CommandHandler.Result.error("Task was already marked "
                    + (isComplete ? "completed." : "incomplete.")
                    + "I'm leaving it as is."
                    + "\n" + this.toDoList.getTask(taskIdx).toString());
        }

        if (isComplete) {
            this.toDoList.setTaskComplete(taskIdx);
        } else {
            this.toDoList.setTaskIncomplete(taskIdx);
        }
        return CommandHandler.Result.success("Roger that. Marking task as "
                + (isComplete ? "completed." : "incomplete.")
                + "\n" + this.toDoList.getTask(taskIdx).toString());
    }

    private CommandHandler.Result deleteTaskCommandHandler(String args) {
        if (this.toDoList.getLength() == 0) {
            return CommandHandler.Result.error("You have no tasks to delete.");
        }

        Optional<Integer> taskIdxOpt;
        taskIdxOpt = this.validateTaskIndex(args);
        if (taskIdxOpt.isEmpty()) {
            return CommandHandler.Result.error(getInvalidTaskIndexMessage());
        }

        Task deletedTask = this.toDoList.deleteTask(taskIdxOpt.get());
        return CommandHandler.Result.success("Roger that. I've deleted the following task from your list:\n"
                + deletedTask.toString()
                + "\nNote, you have " + this.toDoList.getLength() + " tasks in your list.");
    }

    private static String getInvalidTaskIndexMessage() {
        return "Please provide a valid task number.";
    }

    private CommandHandler.Result addTodoCommandHandler(String args) {
        if (args.isBlank()) {
            return CommandHandler.Result.error("Please provide a task description.");
        }

        this.toDoList.addTask(new ToDoTask(args));
        return CommandHandler.Result.success(this.getTaskAddedMessage());
    }

    private CommandHandler.Result addDeadlineCommandHandler(String args) {
        String[] argsSplit = args.split(" /by ", 2);
        if (argsSplit.length < 2) {
            return CommandHandler.Result.error("Please provide a task description and deadline.");
        }

        this.toDoList.addTask(new DeadlineTask(argsSplit[0], argsSplit[1]));
        return CommandHandler.Result.success(this.getTaskAddedMessage());
    }

    private CommandHandler.Result addEventCommandHandler(String args) {
        String[] argsSplit = args.split(" /from ", 2);
        if (argsSplit.length < 2) {
            return CommandHandler.Result.error("Please provide a valid task description, start time and end time.");
        }

        String[] startEndTime = argsSplit[1].split(" /to ", 2);
        if (startEndTime.length < 2) {
            return CommandHandler.Result.error("Please provide a valid start and end time separated by /at.");
        }

        this.toDoList.addTask(new EventTask(argsSplit[0], startEndTime[0], startEndTime[1]));
        return CommandHandler.Result.success(this.getTaskAddedMessage());
    }

    private CommandHandler.Result helpCommandHandler(String args) {
        String[] commands = this.commandHandlerMap.keySet().toArray(new String[0]);

        StringBuilder helpMessage = new StringBuilder("Hi there, here are some commands to get started:\n");
        for (String command : commands) {
            helpMessage.append(this.commandHandlerMap.get(command).getExampleUsage()).append("\n");
        }
        helpMessage.setLength(helpMessage.length() - 1);

        return CommandHandler.Result.success(helpMessage.toString());
    }

    private String getTaskAddedMessage() {
        return "Roger that, I've added the following task to your list:\n"
                + this.toDoList.getTask(this.toDoList.getLength() - 1).toString()
                + "\nNote, you have " + this.toDoList.getLength() + " tasks in your list.";
    }

    private Optional<Integer> validateTaskIndex(String args) {
        int taskIdx;
        try {
            taskIdx = Integer.parseInt(args) - 1;
        } catch (NumberFormatException e) {
            return Optional.empty();
        }

        if (taskIdx < 0 || taskIdx >= this.toDoList.getLength()) {
            return Optional.empty();
        }

        return Optional.of(taskIdx);
    }
}