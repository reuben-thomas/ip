package commandhandler;

import java.util.Optional;

/**
 * Represents the result of a command function execution.
 */
public record CommandResult(Optional<String> response, Optional<String> errorMessage, ResultType resultType) {
    /**
     * Creates a successful command with message response.
     *
     * @param response The response to be returned.
     * @return The successful command result.
     */
    public static CommandResult success(String response) {
        return new CommandResult(Optional.of(response), Optional.empty(), ResultType.SUCCESS);
    }

    /**
     * Creates a command result indicating an invalid usage of the command, such as incorrect arguments.
     *
     * @param errorMessage The error response detailing the invalid usage.
     * @return The invalid usage command result.
     */
    public static CommandResult usageError(String errorMessage) {
        return new CommandResult(Optional.empty(), Optional.of(errorMessage), ResultType.INVALID_USAGE_ERROR);
    }

    /**
     * Creates a command result indicating an unexpected error occurred during command execution.
     *
     * @param errorMessage The error response explaining the unexpected error.
     * @return The unexpected error command result.
     */
    public static CommandResult unexpectedError(String errorMessage) {
        return new CommandResult(Optional.empty(), Optional.of(errorMessage), ResultType.UNEXPECTED_ERROR);
    }

    /**
     * Represents the possible types of result of a command function execution.
     */
    public enum ResultType {
        SUCCESS,
        INVALID_USAGE_ERROR,
        UNEXPECTED_ERROR
    }
}
