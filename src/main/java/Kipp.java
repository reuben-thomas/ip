public class Kipp {
    private static final String NAME = "KIPP";
    private static final String LOGO = """
            ██   ██ ██ ██████  ██████
            ██  ██  ██ ██   ██ ██   ██
            █████   ██ ██████  ██████
            ██  ██  ██ ██      ██
            ██   ██ ██ ██      ██
            """;

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
        } else {
            return input;
        }
    }
}
