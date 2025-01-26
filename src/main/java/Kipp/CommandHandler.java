package Kipp;

import java.util.Optional;

public final class CommandHandler {
    private final String command;
    private final String exampleUsage;
    private final HandlerFunction handlerFunction;

    public CommandHandler(String command, HandlerFunction handlerFunction) {
        this.command = command;
        this.exampleUsage = this.command;
        this.handlerFunction = handlerFunction;
    }

    public CommandHandler(String command, String exampleArgs, HandlerFunction handlerFunction) {
        this.command = command;
        this.exampleUsage = this.command + " " + exampleArgs;
        this.handlerFunction = handlerFunction;
    }

    public String getCommand() {
        return this.command;
    }

    public String getExampleUsage() {
        return this.exampleUsage;
    }

    public String getResponse(String input) {
        String[] inputSplit = input.split(" ", 2);
        String command = inputSplit[0];
        String args = inputSplit.length > 1 ? inputSplit[1] : "";

        Result result = this.handlerFunction.apply(args);

        if (result.response().isPresent()) {
            return result.response().get();
        }
        if (result.errorMessage().isPresent()) {
            return result.errorMessage().orElseGet(() -> "Invalid usage") + "\nExample: " + this.exampleUsage;
        } else {
            throw new IllegalStateException("Result must have either response or error message.");
        }
    }

    @FunctionalInterface
    public interface HandlerFunction {
        Result apply(String args);
    }

    public record Result(Optional<String> response, Optional<String> errorMessage) {
        public static Result success(String response) {
            return new Result(Optional.of(response), Optional.empty());
        }

        public static Result error(String errorMessage) {
            return new Result(Optional.empty(), Optional.of(errorMessage));
        }
    }
}
