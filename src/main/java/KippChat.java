import java.util.Scanner;

import Kipp.*;

public class KippChat {
    private final Kipp kipp;
    private final String username;
    private final Scanner scanner;

    public KippChat() {
        this.kipp = new Kipp();
        // If test username provided in environment variable, use it.
        if (System.getenv("KIPP_CHAT_TEST_USERNAME") != null) {
            this.username = System.getenv("KIPP_CHAT_TEST_USERNAME");
        } else if (System.getProperty("user.name") == null) {
            this.username = System.getProperty("user.name");
        } else {
            this.username = "cooper";
        }
        this.scanner = new Scanner(System.in);
    }

    private static void printNameBadge(String name) {
        System.out.println("[" + name + "]");
    }

    private static void printSeparator() {
        System.out.println("---");
    }

    public static void main(String[] args) {
        KippChat chat = new KippChat();
        chat.run();
    }

    public void run() {
        System.out.println(Kipp.getLogo());
        this.displayMessage(Kipp.getSelfIntroduction());

        String userInput = "";
        while (!userInput.equals("bye")) {
            userInput = this.readUserInput();
            this.displayMessage(this.kipp.getResponse(userInput));
        }
    }

    private void displayMessage(String message) {
        KippChat.printNameBadge(Kipp.getName());
        System.out.println(message);
        KippChat.printSeparator();
    }

    private String readUserInput() {
        KippChat.printNameBadge(this.username);
        String userInput = this.scanner.nextLine();
        KippChat.printSeparator();
        return userInput;
    }
}
