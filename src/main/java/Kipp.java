public class Kipp {
    private static final String NAME = "Kipp";
    private static final String LOGO = """
            ██   ██ ██ ██████  ██████
            ██  ██  ██ ██   ██ ██   ██
            █████   ██ ██████  ██████
            ██  ██  ██ ██      ██
            ██   ██ ██ ██      ██
            """;

    public static void main(String[] args) {
        Kipp.printGreeting();
        Kipp.printSeparator();
        Kipp.printGoodbye();
    }

    public static void printGreeting() {
        System.out.println("Hello! I'm Kipp.");
        System.out.println(Kipp.LOGO);
        System.out.println("How can I assist you today?");
    }

    public static void printGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    public static void printSeparator() {
        System.out.println("------------------------");
    }
}
