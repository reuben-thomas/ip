import java.util.Scanner;

import Kipp.*;

public class Chat {
    private final Kipp kipp;
    private final String username;
    private final Scanner scanner;

    public Chat() {
        this.kipp = new Kipp();
        this.username = System.getProperty("user.name");
        this.scanner = new Scanner(System.in);
    }

    private static void printNameBadge(String name) {
        System.out.println("[" + name + "]");
    }

    private static void printSeparator() {
        System.out.println("---");
    }

    public static void main(String[] args) {
        Chat chat = new Chat();
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
        Chat.printNameBadge(Kipp.getName());
        System.out.println(message);
        Chat.printSeparator();
    }

    private String readUserInput() {
        Chat.printNameBadge(this.username);
        String userInput = this.scanner.nextLine();
        Chat.printSeparator();
        return userInput;
    }
}
