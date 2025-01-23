package Kipp;

import ToDoList.*;

import java.util.HashMap;
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
        this.commandHandlerMap = new HashMap<>();
        this.commandHandlerMap.put("hello", args -> Kipp.getSelfIntroduction());
        this.commandHandlerMap.put("bye", args -> Kipp.getSignOut());
        this.commandHandlerMap.put("list", args -> this.toDoList.toString());
        this.commandHandlerMap.put("mark", this::markCommandHandler);
        this.commandHandlerMap.put("unmark", this::unmarkCommandHandler);
        this.commandHandlerMap.put("todo", this::addTodoCommandHandler);
        this.commandHandlerMap.put("deadline", this::addDeadlineCommandHandler);
        this.commandHandlerMap.put("event", this::addEventCommandHandler);
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
        String[] inputWords = input.split(" ", 2);
        String command = inputWords[0];
        String args = inputWords.length > 1 ? inputWords[1] : "";

        CommandHandler commandHandler = this.commandHandlerMap.get(command);
        if (commandHandler == null) {
            return Kipp.UNRECOGNIZED_COMMAND_MESSAGE;
        } else {
            return commandHandler.getResponse(args);
        }
    }

    private String markCommandHandler(String args) {
        int taskIdx = Integer.parseInt(args) - 1;
        this.toDoList.setTaskComplete(taskIdx);
        return "Good work. I've marked this task as done:\n" + this.toDoList.getTask(taskIdx).toString();
    }

    private String unmarkCommandHandler(String args) {
        int taskIdx = Integer.parseInt(args) - 1;
        this.toDoList.setTaskIncomplete(taskIdx);
        return "Alright. I've marked this task as undone:\n" + this.toDoList.getTask(taskIdx).toString();
    }

    private String addTodoCommandHandler(String args) {
        String taskName = args;
        ToDoTask task = new ToDoTask(taskName);
        this.toDoList.addTask(task);
        return "Roger, I've added this task to the list:\n" + task.toString() + "\nNow you have " + this.toDoList.getLength() + " tasks in the list.";
    }

    private String addDeadlineCommandHandler(String args) {
        String argsSplit[] = args.split(" /by ");
        String taskName = argsSplit[0];
        String deadline = argsSplit[1];
        DeadlineTask task = new DeadlineTask(taskName, deadline);
        this.toDoList.addTask(task);
        return "Roger, I've added this task to the list:\n" + task.toString() + "\nNow you have " + this.toDoList.getLength() + " tasks in the list.";
    }

    private String addEventCommandHandler(String args) {
        String argsSplit[] = args.split(" /from ");
        String taskName = argsSplit[0];
        String[] timeRange = argsSplit[1].split(" /to ");
        EventTask task = new EventTask(taskName, timeRange[0], timeRange[1]);
        this.toDoList.addTask(task);
        return "Roger, I've added this task to the list:\n" + task.toString() + "\nNow you have " + this.toDoList.getLength() + " tasks in the list.";
    }
}