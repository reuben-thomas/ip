package Kipp;

import TaskList.DeadlineTask;
import TaskList.EventTask;
import TaskList.Task;
import TaskList.TaskList;
import TaskList.ToDoTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
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
    private static final String SAVE_FILE_NAME = "KIPP.txt";
    private static final String UNRECOGNIZED_COMMAND_MESSAGE = "I don't recognize that command.";

    private Map<String, CommandHandler> commandHandlerMap;
    private TaskList taskList;

    public Kipp() {
        this.taskList = new TaskList();
        this.initializeCommandHandlerMap();
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

    private static String getInvalidTaskIndexMessage() {
        return "Please provide a valid task number.";
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
                "<task description> /by <deadline yyyy-mm-dd>",
                this::addDeadlineCommandHandler));
        this.commandHandlerMap.put("event", new CommandHandler("event",
                "<task description> /from <start yyyy-mm-dd> /to <end yyyy-mm-dd>",
                this::addEventCommandHandler));
        this.commandHandlerMap.put("delete", new CommandHandler("delete", "<task number>",
                this::deleteTaskCommandHandler));
        this.commandHandlerMap.put("save", new CommandHandler("save", "",
                this::saveCommandHandler));
        this.commandHandlerMap.put("load", new CommandHandler("load", "",
                this::loadCommandHandler));
    }

    public String getResponse(String inputCommand) {
        CommandHandler commandHandler = this.commandHandlerMap.get(inputCommand.split(" ", 2)[0]);
        if (commandHandler == null) {
            return Kipp.UNRECOGNIZED_COMMAND_MESSAGE;
        } else {
            return commandHandler.getResponse(inputCommand);
        }
    }

    public String getResponse(String[] inputCommands) {
        StringBuilder response = new StringBuilder();
        for (String inputCommand : inputCommands) {
            response.append(this.getResponse(inputCommand)).append("\n");
        }
        response.setLength(response.length() - 1);
        return response.toString();
    }

    // Approach to serializing and deserializing objects adapted from:
    // https://www.geeksforgeeks.org/serialization-in-java/
    private CommandHandler.Result saveCommandHandler(String args) {
        try {
            File file = new File(Kipp.SAVE_FILE_NAME);
            if (!file.exists()) {
                file.createNewFile();
            }

            FileOutputStream fileOut = new FileOutputStream(Kipp.SAVE_FILE_NAME);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(this.taskList);
            objectOut.close();
            fileOut.close();
        } catch (Exception e) {
            return CommandHandler.Result.error("Something went wrong, I couldn't save your todo list.");
        }
        return CommandHandler.Result.success("I've saved your todo list to "
                + Kipp.SAVE_FILE_NAME + ".");
    }

    // Approach to serializing and deserializing objects adapted from:
    // https://www.geeksforgeeks.org/serialization-in-java/
    private CommandHandler.Result loadCommandHandler(String args) {
        File file = new File(Kipp.SAVE_FILE_NAME);
        if (!file.exists()) {
            return CommandHandler.Result.error("Sorry, it seems you don't have a saved todo list at "
                    + Kipp.SAVE_FILE_NAME + ". I'm leaving it as is.");
        }

        try {
            FileInputStream fileIn = new FileInputStream(Kipp.SAVE_FILE_NAME);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            this.taskList = (TaskList) objectIn.readObject();
            objectIn.close();
            fileIn.close();
        } catch (Exception e) {
            return CommandHandler.Result.error("Something went wrong, I couldn't load your todo list. I'm leaving it as is.");
        }
        return CommandHandler.Result.success("I've loaded your todo list from "
                + Kipp.SAVE_FILE_NAME + ".");
    }

    private CommandHandler.Result listCommandHandler(String args) {
        return this.taskList.getLength() == 0
                ? CommandHandler.Result.success("You have 0 tasks on your list.")
                : CommandHandler.Result.success(this.taskList.toString());
    }

    private CommandHandler.Result setCompleteCommandHandler(String args) {
        return this.setCompletionCommandHandlerHelper(args, true);
    }

    private CommandHandler.Result setIncompleteCommandHandler(String args) {
        return this.setCompletionCommandHandlerHelper(args, false);
    }

    private CommandHandler.Result setCompletionCommandHandlerHelper(String args, boolean isComplete) {
        if (this.taskList.getLength() == 0) {
            return CommandHandler.Result.error("You have no tasks to delete.");
        }

        Optional<Integer> taskIdxOpt;
        taskIdxOpt = this.validateTaskIndex(args);
        if (taskIdxOpt.isEmpty()) {
            return CommandHandler.Result.error(getInvalidTaskIndexMessage());
        }
        int taskIdx = taskIdxOpt.get();

        if (isComplete == this.taskList.getTask(taskIdx).isCompleted()) {
            return CommandHandler.Result.error("Task was already marked "
                    + (isComplete ? "completed." : "incomplete.")
                    + "I'm leaving it as is."
                    + "\n" + this.taskList.getTask(taskIdx).toString());
        }

        if (isComplete) {
            this.taskList.setTaskComplete(taskIdx);
        } else {
            this.taskList.setTaskIncomplete(taskIdx);
        }
        return CommandHandler.Result.success("Roger that. Marking task as "
                + (isComplete ? "completed." : "incomplete.")
                + "\n" + this.taskList.getTask(taskIdx).toString());
    }

    private CommandHandler.Result deleteTaskCommandHandler(String args) {
        if (this.taskList.getLength() == 0) {
            return CommandHandler.Result.error("You have no tasks to delete.");
        }

        Optional<Integer> taskIdxOpt;
        taskIdxOpt = this.validateTaskIndex(args);
        if (taskIdxOpt.isEmpty()) {
            return CommandHandler.Result.error(getInvalidTaskIndexMessage());
        }

        Task deletedTask = this.taskList.deleteTask(taskIdxOpt.get());
        return CommandHandler.Result.success("Roger that. I've deleted the following task from your list:\n"
                + deletedTask.toString()
                + "\nNote, you have " + this.taskList.getLength() + " tasks in your list.");
    }

    private CommandHandler.Result addTodoCommandHandler(String args) {
        if (args.isBlank()) {
            return CommandHandler.Result.error("Please provide a task description.");
        }

        this.taskList.addTask(new ToDoTask(args));
        return CommandHandler.Result.success(this.getTaskAddedMessage());
    }

    private CommandHandler.Result addDeadlineCommandHandler(String args) {
        String[] argsSplit = args.split(" /by ", 2);
        if (argsSplit.length < 2) {
            return CommandHandler.Result.error("Please provide a task description and deadline.");
        }

        LocalDate deadlineDate;
        try {
            deadlineDate = LocalDate.parse(argsSplit[1]);
        } catch (Exception e) {
            return CommandHandler.Result.error("Please provide a valid deadline in the format yyyy-mm-dd.");
        }

        this.taskList.addTask(new DeadlineTask(argsSplit[0], deadlineDate));
        return CommandHandler.Result.success(this.getTaskAddedMessage());
    }

    private CommandHandler.Result addEventCommandHandler(String args) {
        String[] argsSplit = args.split(" /from ", 2);
        if (argsSplit.length < 2) {
            return CommandHandler.Result.error("Please provide a valid task description, start time and end time.");
        }

        String[] startEndDate = argsSplit[1].split(" /to ", 2);
        LocalDate startDate;
        LocalDate endDate;
        try {
            if (startEndDate.length < 2) {
                throw new IllegalArgumentException("Insufficient arguments.");
            }
            startDate = LocalDate.parse(startEndDate[0]);
            endDate = LocalDate.parse(startEndDate[1]);
        } catch (Exception e) {
            return CommandHandler.Result.error("Please provide a valid start and end time in the format yyyy-mm-dd, separated by /to.");
        }

        this.taskList.addTask(new EventTask(argsSplit[0], startDate, endDate));
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
                + this.taskList.getTask(this.taskList.getLength() - 1).toString()
                + "\nNote, you have " + this.taskList.getLength() + " tasks in your list.";
    }

    private Optional<Integer> validateTaskIndex(String args) {
        int taskIdx;
        try {
            taskIdx = Integer.parseInt(args) - 1;
        } catch (NumberFormatException e) {
            return Optional.empty();
        }

        if (taskIdx < 0 || taskIdx >= this.taskList.getLength()) {
            return Optional.empty();
        }

        return Optional.of(taskIdx);
    }
}