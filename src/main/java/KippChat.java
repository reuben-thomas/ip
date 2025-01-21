import java.util.Scanner;

public class KippChat {
    private final Kipp kipp;
    private final String username;
    private final Scanner scanner;

    public KippChat() {
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
