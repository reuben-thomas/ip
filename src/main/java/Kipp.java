import java.util.ArrayList;
import java.util.List;

public class Kipp {
    private static final String NAME = "KIPP";
    private static final String LOGO = """
            ██   ██ ██ ██████  ██████
            ██  ██  ██ ██   ██ ██   ██
            █████   ██ ██████  ██████
            ██  ██  ██ ██      ██
            ██   ██ ██ ██      ██
            """;

    private List<String> toDoList;

    public Kipp() {
        this.toDoList = new ArrayList<String>();
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
            return this.getToDoListItems();
        } else {
            return addToDoListItems(input);
        }
    }

    public String addToDoListItems(String item) {
        this.toDoList.add(item);
        return "added: " + item;
    }

    public String getToDoListItems() {
        StringBuilder toDoListItems = new StringBuilder();
        for (int i = 0; i < this.toDoList.size(); i++) {
            toDoListItems.append((i + 1) + ". " + this.toDoList.get(i));
            if (i != this.toDoList.size() - 1) {
                toDoListItems.append("\n");
            }
        }
        return toDoListItems.toString();
    }
}
