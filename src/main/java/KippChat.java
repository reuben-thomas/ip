import java.util.Scanner;

import Kipp.*;

public class KippChat {
    private final Kipp kipp;
    private final String username;
    private final Scanner scanner;

    public KippChat() {
        // If test username provided in environment variable, use it.
        if (System.getenv("KIPP_CHAT_TEST_USERNAME") != null) {
            this.username = System.getenv("KIPP_CHAT_TEST_USERNAME");
        } else if (System.getProperty("user.name") != null) {
            this.username = System.getProperty("user.name");
        } else {
            this.username = "cooper";
        }
        this.scanner = new Scanner(System.in);
        this.kipp = new Kipp();
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

    private void displayMessage(String[] messages) {
        KippChat.printNameBadge(Kipp.getName());
        for (String message : messages) {
            System.out.println(message);
        }
        KippChat.printSeparator();
    }

    private String readUserInput() {
        KippChat.printNameBadge(this.username);
        String userInput = this.scanner.nextLine();
        KippChat.printSeparator();
        return userInput;
    }
}
