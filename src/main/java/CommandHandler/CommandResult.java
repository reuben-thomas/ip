package CommandHandler;

import java.util.Optional;

public record CommandResult(Optional<String> response, Optional<String> errorMessage, ResultType resultType) {
    public static CommandResult success(String response) {
        return new CommandResult(Optional.of(response), Optional.empty(), ResultType.SUCCESS);
    }

    public static CommandResult usageError(String errorMessage) {
        return new CommandResult(Optional.empty(), Optional.of(errorMessage), ResultType.INVALID_USAGE_ERROR);
    }

    public static CommandResult unexpectedError(String errorMessage) {
        return new CommandResult(Optional.empty(), Optional.of(errorMessage), ResultType.UNEXPECTED_ERROR);
    }

    public enum ResultType {
        SUCCESS,
        INVALID_USAGE_ERROR,
        UNEXPECTED_ERROR
    }
}
