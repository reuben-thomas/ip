package Kipp;

@FunctionalInterface
public interface CommandHandler {
    String getResponse(String args);
}
