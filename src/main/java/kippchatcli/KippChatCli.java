package kippchatcli;

import java.util.Scanner;

import kipp.Kipp;

/**
 * Represents the chat interface for interacting with Kipp the robot.
 */
public class KippChatCli {
    private final Kipp kipp;
    private final String username;
    private final Scanner scanner;

    /**
     * Constructor for KippChatCli class.
     */
    private KippChatCli() {
        // If test username provided in environment variable, use it.
        if (System.getenv("KIPP_CHAT_TEST_USERNAME") != null) {
            this.username = System.getenv("KIPP_CHAT_TEST_USERNAME");
        } else if (System.getProperty("user.name") != null) {
            this.username = System.getProperty("user.name");
        } else {
            this.username = "Dr. Mann";
        }
        this.scanner = new Scanner(System.in);
        this.kipp = Kipp.createKipp();
    }

    /**
     * Factory method to create a new KippChatCli instance.
     *
     * @return The new KippChatCli instance.
     */
    public static KippChatCli createKippChatCli() {
        return new KippChatCli();
    }


    private static void printNameBadge(String name) {
        System.out.println("[" + name + "]");
    }

    private static void printSeparator() {
        System.out.println("---");
    }

    public static void main(String[] args) {
        KippChatCli chat = new KippChatCli();
        chat.run();
    }

    /**
     * Runs the chat interface.
     */
    public void run() {
        System.out.println(Kipp.getLogo());

        this.kipp.getResponse("load");

        this.displayMessage(new String[]{
                this.kipp.getResponse("hello"),
        });

        String userInput = "";
        while (!userInput.startsWith("bye")) {
            userInput = this.readUserInput();
            this.displayMessage(new String[]{this.kipp.getResponse(userInput)});
        }

        this.kipp.getResponse("save");
    }

    /**
     * Displays messages to the user in CLI, neatly formatted with the appropriate name badge and separator.
     *
     * @param messages The messages to display.
     */
    private void displayMessage(String[] messages) {
        KippChatCli.printNameBadge(Kipp.getName());
        for (String message : messages) {
            System.out.println(message);
        }
        KippChatCli.printSeparator();
    }

    /**
     * Reads user input from CLI.
     *
     * @return The user input.
     */
    private String readUserInput() {
        KippChatCli.printNameBadge(this.username);
        String userInput = this.scanner.nextLine();
        KippChatCli.printSeparator();
        return userInput;
    }
}
