package kipp;

import java.io.File;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import commandhandler.Command;
import commandhandler.CommandHandler;
import commandhandler.CommandResult;
import storage.Storage;
import storage.StorageException;
import tasklist.DeadlineTask;
import tasklist.EventTask;
import tasklist.Task;
import tasklist.TaskList;
import tasklist.ToDoTask;

/**
 * Represents the main class for KIPP, an assistant with task management capabilities.
 */
public class Kipp {
    private static final String LOGO = """
            ██   ██ ██ ██████  ██████
            ██  ██  ██ ██   ██ ██   ██
            █████   ██ ██████  ██████
            ██  ██  ██ ██      ██
            ██   ██ ██ ██      ██
            """;
    private static final String NAME = "KIPP";
    private static final String TASK_LIST_SAVE_FILE_PATH = "KIPP.txt";
    private static final String SIGN_OUT_MESSAGE = "Goodbye. Safe travels.";
    private static final String INVALID_TASK_INDEX_MESSAGE = "Please provide a valid task number.";
    private TaskList taskList;
    private CommandHandler commandHandler;

    /**
     * Constructor for Kipp class.
     */
    private Kipp() {
        this.taskList = new TaskList();
        this.initializeCommandHandlerMap();
    }

    /**
     * Factory method to create a new Kipp instance.
     *
     * @return The new Kipp instance.
     */
    public static Kipp createKipp() {
        return new Kipp();
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
        return Kipp.SIGN_OUT_MESSAGE;
    }

    /**
     * Checks if the filepath is absolute, is valid, and is a text file.
     *
     * @param filePath
     * @return
     */
    private static Optional<String> getErrorIfInvalidFilePath(String filePath) {
        if (!filePath.endsWith(".txt")) {
            return Optional.of("Please provide a valid file path to a text file" + " with the .txt extension.");
        }

        File file = new File(filePath);
        if (file.isAbsolute()) {
            return Optional.of("Please provide a relative file path." + "Absolute file paths are not allowed.");
        }

        return Optional.empty();
    }

    private void initializeCommandHandlerMap() {
        this.commandHandler = CommandHandler.createCommandHandler(true);
        this.commandHandler.addCommand(Command.createCommandWithoutArgs(
                "hello",
                "greeting from KIPP",
                args -> CommandResult.createSuccessResult(Kipp.getSelfIntroduction())));
        this.commandHandler.addCommand(Command.createCommandWithoutArgs(
                "bye",
                "save task list and exit",
                args -> CommandResult.createSuccessResult(Kipp.getSignOut())));
        this.commandHandler.addCommand(Command.createCommandWithoutArgs(
                "list",
                "list all tasks on your list.",
                this::listCommandHandler));
        this.commandHandler.addCommand(Command.createCommandWithArgs(
                "mark",
                "<task number>",
                "set task as completed",
                this::setCompleteCommandHandler));
        this.commandHandler.addCommand(Command.createCommandWithArgs(
                "unmark",
                "<task number>",
                "set task as incomplete",
                this::setIncompleteCommandHandler));
        this.commandHandler.addCommand(Command.createCommandWithArgs(
                "todo",
                "<task description>",
                "add a todo task to your list",
                this::addTodoCommandHandler));
        this.commandHandler.addCommand(Command.createCommandWithArgs(
                "deadline",
                "<task description> /by <deadline yyyy-mm-dd>",
                "add task with deadline to your list",
                this::addDeadlineCommandHandler));
        this.commandHandler.addCommand(Command.createCommandWithArgs(
                "event",
                "<task description> /from <start yyyy-mm-dd> /to <end yyyy-mm-dd>",
                "add task with deadline to your list",
                this::addEventCommandHandler));
        this.commandHandler.addCommand(Command.createCommandWithArgs(
                "delete",
                "<task number>",
                "delete task by task number",
                this::deleteTaskCommandHandler));
        this.commandHandler.addCommand(Command.createCommandWithArgs(
                "saveto",
                "<relative_file_path.txt>",
                "save current task list to disk as a text file",
                this::saveCommandHandler));
        this.commandHandler.addCommand(Command.createCommandWithArgs(
                "loadfrom",
                "<relative_file_path.txt>",
                "load previously saved task list from disk as a text file",
                this::loadCommandHandler));
        this.commandHandler.addCommand(Command.createCommandWithArgs(
                "find",
                "<keyword>",
                "find tasks containing keyword",
                this::findTaskCommandHandler));
    }

    public String getResponse(String input) {
        return this.commandHandler.getResponse(input);
    }

    /**
     * Gets the response from the appropriate command handler function given an array of input commands.
     *
     * @param inputCommands The array of input commands.
     * @return The response from the command after executing the handler function.
     */
    public String getResponse(String[] inputCommands) {
        return Arrays.stream(inputCommands).map(this::getResponse).collect(Collectors.joining("\n"));
    }

    /**
     * Handles the save command, to save the current task list to disk.
     * Approach to serializing and deserializing objects adapted from:
     * <a href="https://www.geeksforgeeks.org/serialization-in-java/">GeeksforGeeks</a>
     *
     * @param filePath The arguments passed to the save command, which will be ignored.
     * @return The result of the save command.
     */
    private CommandResult saveCommandHandler(String filePath) {
        Optional<String> filePathError = Kipp.getErrorIfInvalidFilePath(filePath);
        if (filePathError.isPresent()) {
            return CommandResult.createUsageErrorResult(filePathError.get());
        }

        try {
            Storage<TaskList> taskListStorage = new Storage<>(filePath, TaskList.class);
            taskListStorage.save(this.taskList);
        } catch (StorageException e) {
            return CommandResult.createUnexpectedErrorResult(
                    "Something went wrong, I couldn't save your task list to "
                            + filePath + ". I'm leaving it as is.");
        }

        return CommandResult.createSuccessResult("I've saved your task list to " + filePath + ".");
    }

    /**
     * Handles the load command, to load the task list from disk.
     * Approach to serializing and deserializing objects adapted from:
     * <a href="https://www.geeksforgeeks.org/serialization-in-java/">GeeksforGeeks</a>
     *
     * @param filePath The arguments passed to the load command, which will be ignored.
     * @return The result of the load command.
     */
    private CommandResult loadCommandHandler(String filePath) {
        Optional<String> filePathError = Kipp.getErrorIfInvalidFilePath(filePath);
        if (filePathError.isPresent()) {
            return CommandResult.createUsageErrorResult(filePathError.get());
        }

        File file = new File(filePath);
        if (!file.exists()) {
            return CommandResult.createUsageErrorResult("The file " + filePath + " does not exist.");
        }

        try {
            Storage<TaskList> taskListStorage = new Storage<>(filePath, TaskList.class);
            this.taskList = taskListStorage.load();
        } catch (StorageException e) {
            return CommandResult.createUnexpectedErrorResult(
                    "Something went wrong, I couldn't load your task list from "
                            + filePath + ". I'm leaving it as is.");
        }
        return CommandResult.createSuccessResult("I've loaded your task list from " + filePath + ".");
    }

    /**
     * Handles the list command, to list all tasks on the task list.
     *
     * @param args The arguments passed to the list command, which will be ignored.
     * @return The result of the list command.
     */
    private CommandResult listCommandHandler(String args) {
        return this.taskList.getLength() == 0 ? CommandResult.createSuccessResult(
                "You have 0 tasks on your list.")
                : CommandResult.createSuccessResult(this.taskList.toString());
    }

    /**
     * Handles the mark command, to set a task as completed.
     *
     * @param args The arguments passed to the mark command.
     * @return The result of the mark command.
     */
    private CommandResult setCompleteCommandHandler(String args) {
        return this.setCompletionCommandHandlerHelper(args, true);
    }

    /**
     * Handles the unmark command, to set a task as incomplete.
     *
     * @param args The arguments passed to the unmark command.
     * @return The result of the unmark command.
     */
    private CommandResult setIncompleteCommandHandler(String args) {
        return this.setCompletionCommandHandlerHelper(args, false);
    }

    private CommandResult setCompletionCommandHandlerHelper(String args, boolean isComplete) {
        Optional<String> taskIdxError = this.getErrorIfInvalidTaskIndex(args);
        if (taskIdxError.isPresent()) {
            return CommandResult.createUsageErrorResult(taskIdxError.get());
        }

        int taskIdx = Integer.parseInt(args) - 1;

        if (isComplete == this.taskList.getTask(taskIdx).isCompleted()) {
            return CommandResult.createUsageErrorResult(
                    "Task was already marked " + (isComplete ? "completed." : "incomplete.")
                            + "I'm leaving it as is." + "\n" + this.taskList.getTask(taskIdx).toString());
        }

        if (isComplete) {
            this.taskList.setTaskComplete(taskIdx);
        } else {
            this.taskList.setTaskIncomplete(taskIdx);
        }
        return CommandResult.createSuccessResult(
                "Roger that. Marking task as " + (isComplete ? "completed." : "incomplete.")
                        + "\n" + this.taskList.getTask(taskIdx).toString());
    }

    private CommandResult deleteTaskCommandHandler(String args) {
        Optional<String> taskIdxError = this.getErrorIfInvalidTaskIndex(args);
        if (taskIdxError.isPresent()) {
            return CommandResult.createUsageErrorResult(taskIdxError.get());
        }

        int taskIdx = Integer.parseInt(args) - 1;
        Task deletedTask = this.taskList.deleteTask(taskIdx);

        return CommandResult.createSuccessResult(
                "Roger that. I've deleted the following task from your list:\n"
                        + deletedTask.toString() + "\nNote, you have "
                        + this.taskList.getLength() + " tasks in your list.");
    }

    private Optional<String> getErrorIfInvalidTaskIndex(String taskIdxStr) {
        int taskIdx;

        try {
            taskIdx = Integer.parseInt(taskIdxStr) - 1;
        } catch (NumberFormatException e) {
            return Optional.of("Please provide a valid task number.");
        }

        if (taskIdx < 0 || taskIdx >= this.taskList.getLength()) {
            return Optional.of("A task with the number "
                    + (taskIdx + 1) + " does not exist."
                    + " Please provide a " + "valid task number.");
        }

        return Optional.empty();
    }

    private CommandResult addTodoCommandHandler(String args) {
        if (args.isBlank()) {
            return CommandResult.createUsageErrorResult(
                    "Please provide a non-empty task description.");
        }

        this.taskList.addTask(new ToDoTask(args));
        return CommandResult.createSuccessResult(this.getTaskAddedMessage());
    }

    private CommandResult addDeadlineCommandHandler(String args) {
        String[] argsSplit = args.split(" /by ", 2);
        if (argsSplit.length < 2) {
            return CommandResult.createUsageErrorResult(
                    "Please provide a task description and deadline.");
        }

        LocalDate deadlineDate;
        try {
            deadlineDate = LocalDate.parse(argsSplit[1]);
        } catch (Exception e) {
            return CommandResult.createUsageErrorResult(
                    "Please provide a valid deadline in the format yyyy-mm-dd.");
        }

        this.taskList.addTask(new DeadlineTask(argsSplit[0], deadlineDate));
        return CommandResult.createSuccessResult(this.getTaskAddedMessage());
    }

    private CommandResult addEventCommandHandler(String args) {
        String[] argsSplit = args.split(" /from ", 2);
        if (argsSplit.length < 2) {
            return CommandResult.createUsageErrorResult(
                    "Please provide a valid task description, start time and end time.");
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
            return CommandResult.createUsageErrorResult(
                    "Please provide a valid start and end time in the format "
                            + "yyyy-mm-dd, separated by /to.");
        }

        this.taskList.addTask(new EventTask(argsSplit[0], startDate, endDate));
        return CommandResult.createSuccessResult(this.getTaskAddedMessage());
    }

    private CommandResult findTaskCommandHandler(String args) {
        if (args.isBlank()) {
            return CommandResult.createUsageErrorResult("Please provide a keyword to search for.");
        }

        TaskList resultTaskList = new TaskList();
        IntStream.range(0, this.taskList.getLength())
                .filter(i -> this.taskList.getTask(i).getTaskName().contains(args))
                .forEach(i -> resultTaskList.addTask(this.taskList.getTask(i)));

        if (resultTaskList.getLength() == 0) {
            return CommandResult.createSuccessResult(
                    "No tasks found containing the keyword: " + args);
        }

        return CommandResult.createSuccessResult(
                "Roger, here are the tasks I've found matching the keyword: " + args + "\n" + resultTaskList);
    }

    private String getTaskAddedMessage() {
        return "Roger that, I've added the following task to your list:\n"
                + this.taskList.getTask(this.taskList.getLength() - 1).toString()
                + "\nNote, you have " + this.taskList.getLength() + " tasks in your list.";
    }
}
