import ToDoList.ToDoList;

public class Kipp {
    private static final String NAME = "KIPP";
    private static final String LOGO = """
            ██   ██ ██ ██████  ██████
            ██  ██  ██ ██   ██ ██   ██
            █████   ██ ██████  ██████
            ██  ██  ██ ██      ██
            ██   ██ ██ ██      ██
            """;

    private ToDoList toDoList;

    public Kipp() {
        this.toDoList = new ToDoList();
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
        if (input.equals("bye")) {
            return Kipp.getSignOut();
        } else if (input.equals("list")) {
            return this.toDoList.toDisplayString();
        } else if (input.startsWith("mark ")) {
            int taskIdx = Integer.parseInt(input.split(" ")[1]) - 1;
            this.toDoList.markTaskCompleted(taskIdx);
            return "Good work, I've marked this completed.\n" + this.toDoList.getTaskDisplayString(taskIdx);
        } else if (input.startsWith("unmark ")) {
            int taskIdx = Integer.parseInt(input.split(" ")[1]) - 1;
            this.toDoList.markTaskIncomplete(taskIdx);
            return "Alright, I've marked this incomplete.\n" + this.toDoList.getTaskDisplayString(taskIdx);
        }

        this.toDoList.addTask(input);
        return "added: " + input;
    }
}
