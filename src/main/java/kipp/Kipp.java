package kipp;

import java.io.File;
import java.time.LocalDate;
import java.util.Optional;
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
    /**
     * ASCII art representation of the KIPP logo.
     */
    private static final String LOGO = """
            ██   ██ ██ ██████  ██████
            ██  ██  ██ ██   ██ ██   ██
            █████   ██ ██████  ██████
            ██  ██  ██ ██      ██
            ██   ██ ██ ██      ██
            """;
    /**
     * The name that KIPP goes by.
     */
    private static final String NAME = "KIPP";
    /**
     * A default name for the user KIPP will use, if non is provided.
     */
    private static final String DEFAULT_USER_NAME = "Dr. Mann";
    /**
     * The name of the user interacting with KIPP.
     */
    private String userName;
    /**
     * The list of tasks that KIPP manages and manipulates.
     */
    private TaskList taskList;
    /**
     * Command handler used to store functions that respond to each function, and to process user input accordingly
     * to generate a response.
     */
    private CommandHandler commandHandler;

    /**
     * Constructor for Kipp class.
     *
     * @param userName The name of the user interacting with KIPP.
     */
    private Kipp(String userName) {
        if (System.getenv("KIPP_CHAT_TEST_USERNAME") != null) {
            this.userName = System.getenv("KIPP_CHAT_TEST_USERNAME");
        } else {
            this.userName = userName;
        }

        this.taskList = new TaskList();
        this.initializeCommandHandlerMap();
    }

    /**
     * Constructor for Kipp class, with a default username.
     */
    private Kipp() {
        this(DEFAULT_USER_NAME);
    }

    /**
     * Factory method to create a new Kipp instance.
     *
     * @return The new Kipp instance.
     */
    public static Kipp createKipp() {
        return new Kipp();
    }

    /**
     * Factory method to create a new Kipp instance, with a specified username to be interacting with.
     *
     * @param userName The name of the user interacting with KIPP.
     * @return The new Kipp instance.
     */
    public static Kipp createKipp(String userName) {
        assert !userName.isBlank() : "Username cannot be empty.";
        return new Kipp(userName);
    }

    public static String getName() {
        return Kipp.NAME;
    }

    /**
     * Gets the ASCII art representation of the KIPP.
     *
     * @return ASCII art of KIPP.
     */
    public static String getLogo() {
        return Kipp.LOGO;
    }

    /**
     * Gets the username of the user interacting with KIPP.
     *
     * @return The username of the user.
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     * Returns the message to be displayed when a user closes the application.
     *
     * @return The sign-out message.
     */
    private String getSignOut() {
        return String.format(
                "Goodbye %s and venture safely. I'll be here if you need me.",
                this.userName);
    }

    /**
     * Returns the message of KIPP introducing themselves to the user.
     *
     * @return The self-introduction message.
     */
    private String getSelfIntroduction() {
        return String.format(
                "Well, hello there %s. This is %s speaking.\nHow can I help?",
                this.userName, Kipp.NAME);
    }

    /**
     * Initializes a new command handler map, and adds all commands, their corresponding handlers functions,
     * and their descriptions.
     */
    private void initializeCommandHandlerMap() {
        this.commandHandler = CommandHandler.createCommandHandler(true);
        this.commandHandler.addCommand(Command.createCommandWithoutArgs(
                "hello",
                "greeting from KIPP",
                args -> CommandResult.createSuccessResult(this.getSelfIntroduction())));
        this.commandHandler.addCommand(Command.createCommandWithoutArgs(
                "bye",
                "save task list and exit",
                args -> CommandResult.createSuccessResult(this.getSignOut())));
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

    /**
     * Processes the input command as Kipp, and returns the response.
     *
     * @param input The input passed to Kipp.
     * @return The response generated by Kipp.
     */
    public String getResponse(String input) {
        return this.commandHandler.getResponse(input);
    }

    /**
     * Handles the save command, to save the current task list to disk.
     *
     * @param filePath The arguments passed to the save command, which will be ignored.
     * @return The result of the save command.
     */
    private CommandResult saveCommandHandler(String filePath) {
        return this.loadSaveCommandHanlderHelper(filePath, false);
    }

    /**
     * Handles the load command, to load the task list from disk.
     *
     * @param filePath The arguments passed to the load command, which will be ignored.
     * @return The result of the load command.
     */
    private CommandResult loadCommandHandler(String filePath) {
        return this.loadSaveCommandHanlderHelper(filePath, true);
    }

    /**
     * A helper command to handle both save and load commands.
     * The approach to serializing and deserializing objects was adapted from:
     * <a href="https://www.geeksforgeeks.org/serialization-in-java/">GeeksforGeeks</a>
     *
     * @param filePath
     * @param isLoad
     * @return
     */
    private CommandResult loadSaveCommandHanlderHelper(String filePath, boolean isLoad) {
        String action = isLoad ? "load" : "save";
        String preposition = isLoad ? "from" : "to";

        Optional<String> filePathError = this.getErrorIfInvalidFilePath(filePath);
        if (filePathError.isPresent()) {
            return CommandResult.createUsageErrorResult(filePathError.get());
        }

        if (isLoad && !new File(filePath).exists()) {
            return CommandResult.createUsageErrorResult(String.format("I'm sorry %s, this file %s doesn't exist.",
                    this.userName, filePath));
        }

        try {
            Storage<TaskList> taskListStorage = new Storage<>(filePath, TaskList.class);
            if (isLoad) {
                this.taskList = taskListStorage.load();
            } else {
                taskListStorage.save(this.taskList);
            }
        } catch (StorageException e) {
            return CommandResult.createUnexpectedErrorResult(
                    String.format("Sorry %s, I'm afraid something's wrong. I couldn't %s your task list %s %s.",
                            this.userName, action, preposition, filePath));
        }

        return CommandResult.createSuccessResult(String.format("Roger that %s. I've %s your task list %s %s.",
                this.userName, action, preposition, filePath)
        );
    }

    /**
     * Checks is a file path is valid, and returns an explanation of the error if it is not.
     *
     * @param filePath The file path to be checked.
     * @return An optional error message if the file path is invalid.
     */
    private Optional<String> getErrorIfInvalidFilePath(String filePath) {
        if (!filePath.endsWith(".txt")) {
            return Optional.of(String.format("Remember %s! I can only save and load files with a .txt extension.",
                    this.userName));
        }

        File file = new File(filePath);
        if (file.isAbsolute()) {
            return Optional.of(String.format("Sorry %s, I can only save and load files with a relative path. "
                            + "You've given me an absolute one.",
                    this.userName));
        }

        return Optional.empty();
    }

    /**
     * Handles the list command, to list all tasks on the task list.
     *
     * @param args The arguments passed to the list command, which will be ignored.
     * @return The result of the list command.
     */
    private CommandResult listCommandHandler(String args) {
        return this.taskList.getLength() == 0 ? CommandResult.createSuccessResult(
                String.format("Lucky you %s! Guess you're done for the day.", this.userName))
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

    /**
     * Helper method to set a task as complete or incomplete.
     *
     * @param args       The index of the task to be marked as complete or incomplete.
     * @param isComplete True if the task is to be marked as complete, false otherwise.
     * @return The result of the set completion command.
     */
    private CommandResult setCompletionCommandHandlerHelper(String args, boolean isComplete) {
        Optional<String> taskIdxError = this.getErrorIfInvalidTaskIndex(args);
        if (taskIdxError.isPresent()) {
            return CommandResult.createUsageErrorResult(taskIdxError.get());
        }

        int taskIdx = Integer.parseInt(args) - 1;

        if (isComplete == this.taskList.getTask(taskIdx).isCompleted()) {
            return CommandResult.createUsageErrorResult(
                    String.format("Hey %s! The task you asked for was already marked %s. I'm leaving it as is:\n%s",
                            this.userName, isComplete ? "completed" : "incomplete",
                            this.taskList.getTask(taskIdx).toString()));
        }

        if (isComplete) {
            this.taskList.setTaskComplete(taskIdx);
        } else {
            this.taskList.setTaskIncomplete(taskIdx);
        }
        return CommandResult.createSuccessResult(
                String.format("Roger that %s. Marking task as %s, good work!\n%s",
                        this.userName, isComplete ? "completed" : "incomplete",
                        this.taskList.getTask(taskIdx).toString()));
    }

    /**
     * Handles the delete command, to delete a task from the task list.
     *
     * @param args The index of the task to be deleted.
     * @return The result of the delete command.
     */
    private CommandResult deleteTaskCommandHandler(String args) {
        Optional<String> taskIdxError = this.getErrorIfInvalidTaskIndex(args);
        if (taskIdxError.isPresent()) {
            return CommandResult.createUsageErrorResult(taskIdxError.get());
        }

        int taskIdx = Integer.parseInt(args) - 1;
        Task deletedTask = this.taskList.deleteTask(taskIdx);

        return CommandResult.createSuccessResult(
                String.format("Roger that %s. I've deleted the following task from your list:"
                                + "\n%s\nYou still have %d tasks in your roster.",
                        this.userName, deletedTask.toString(), this.taskList.getLength()));
    }

    /**
     * Returns an error message if the task index is invalid.
     *
     * @param taskIdxStr The task index string.
     * @return An optional message describing the error if the task index is invalid.
     */
    private Optional<String> getErrorIfInvalidTaskIndex(String taskIdxStr) {
        int taskIdx;

        try {
            taskIdx = Integer.parseInt(taskIdxStr) - 1;
        } catch (NumberFormatException e) {
            return Optional.of(String.format("%s! This is no time for games. Please provide a valid task number.",
                    this.userName));
        }

        if (taskIdx < 0 || taskIdx >= this.taskList.getLength()) {
            return Optional.of(String.format("Are you alright %s? A task with the number %d does not exist. "
                            + "Please provide a valid task number.",
                    this.userName, taskIdx + 1));
        }

        return Optional.empty();
    }

    /**
     * Handles the addition of a todo task to the task list.
     *
     * @param args The task description of the todo task.
     * @return The result of the add todo command.
     */
    private CommandResult addTodoCommandHandler(String args) {
        if (args.isBlank()) {
            return CommandResult.createUsageErrorResult(
                    String.format("Dear %s, I can't add an empty task to your list. Please provide a task description.",
                            this.userName));
        }

        this.taskList.addTask(new ToDoTask(args));
        return CommandResult.createSuccessResult(this.getTaskAddedMessage());
    }

    /**
     * Handles the add deadline command, to add a deadline task to the task list.
     *
     * @param args The arguments needed to add a deadline task in the form "task description /by deadline".
     * @return The result of the add deadline command.
     */
    private CommandResult addDeadlineCommandHandler(String args) {
        String[] argsSplit = args.split(" /by ", 2);
        if (argsSplit.length < 2) {
            return CommandResult.createUsageErrorResult(
                    String.format("Focus %s! I need a task description and a deadline to add a task.", this.userName));
        }

        LocalDate deadlineDate;
        try {
            deadlineDate = LocalDate.parse(argsSplit[1]);
        } catch (Exception e) {
            return CommandResult.createUsageErrorResult(
                    String.format("%s, I need a valid deadline in the format yyyy-mm-dd.", this.userName));
        }

        this.taskList.addTask(new DeadlineTask(argsSplit[0], deadlineDate));
        return CommandResult.createSuccessResult(this.getTaskAddedMessage());
    }

    /**
     * Handles the add event command, to add an event task to the task list.
     *
     * @param args The arguments needed to create an event in the format "task description /from start /to end".
     * @return The result of the add event command.
     */
    private CommandResult addEventCommandHandler(String args) {
        String[] argsSplit = args.split(" /from ", 2);
        if (argsSplit.length < 2) {
            return CommandResult.createUsageErrorResult(
                    String.format("%s, I need a task description and a start and end time to add an event.",
                            this.userName));
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
                    String.format("Listen to me %s! I need a valid start and end date from you.",
                            this.userName));
        }

        this.taskList.addTask(new EventTask(argsSplit[0], startDate, endDate));
        return CommandResult.createSuccessResult(this.getTaskAddedMessage());
    }

    /**
     * Handles the find command, to search for tasks containing a keyword.
     *
     * @param args The search term used to find tasks.
     * @return The result of the find command.
     */
    private CommandResult findTaskCommandHandler(String args) {
        if (args.isBlank()) {
            return CommandResult.createUsageErrorResult(String.format(
                    "%s, I can't help you find nothing. "
                            + "Please provide a keyword to search for.", this.userName));
        }

        TaskList resultTaskList = new TaskList();
        IntStream.range(0, this.taskList.getLength())
                .filter(i -> this.taskList.getTask(i).getTaskName().contains(args))
                .forEach(i -> resultTaskList.addTask(this.taskList.getTask(i)));

        if (resultTaskList.getLength() == 0) {
            return CommandResult.createSuccessResult(
                    String.format("Oh no %s! No tasks found containing the keyword: %s", this.userName, args));
        }

        return CommandResult.createSuccessResult(
                String.format("Roger, here are the tasks I've found matching the keyword: %s\n%s",
                        args, resultTaskList.toString()));
    }

    /**
     * Returns the message to be displayed after a task has been added to the task list.
     *
     * @return Formatted message to show successful task addition.
     */
    private String getTaskAddedMessage() {
        return String.format("Roger that %s, I've added the following task to your list:\n"
                        + "%s\nNote, you have %d tasks in your list.",
                this.userName, this.taskList.getTask(this.taskList.getLength() - 1).toString(),
                this.taskList.getLength());
    }
}
