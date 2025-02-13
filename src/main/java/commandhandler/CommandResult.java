package commandhandler;

import java.util.Optional;

/**
 * Represents the result of a command function execution.
 */
public record CommandResult(Optional<String> response, Optional<String> errorMessage, ResultType resultType) {

    /**
     * Constructs a new command result.
     *
     * @param response     The response to be returned.
     * @param errorMessage The error message to be returned.
     * @param resultType   The type of the result.
     */
    public CommandResult(Optional<String> response, Optional<String> errorMessage, ResultType resultType) {
        if (resultType != ResultType.SUCCESS) {
            assert errorMessage.isPresent() && !errorMessage.get().isBlank()
                    : "Error message must be present and non-empty for non-successful command results.";
            assert response.isEmpty()
                    : "Response must be non-empty for non-successful command results.";
        } else {
            assert errorMessage.isEmpty()
                    : "Successful command result must not have an error message.";
            assert response.isPresent() && !response.get().isBlank()
                    : "Successful command result must have a non-empty response.";
        }

        this.response = response;
        this.errorMessage = errorMessage;
        this.resultType = resultType;
    }

    /**
     * Creates a successful command with message response.
     *
     * @param response The response to be returned.
     * @return The successful command result.
     */
    public static CommandResult createSuccessResult(String response) {
        return new CommandResult(Optional.of(response), Optional.empty(), ResultType.SUCCESS);
    }

    /**
     * Creates a command result indicating an invalid usage of the command, such as incorrect arguments.
     *
     * @param errorMessage The error response detailing the invalid usage.
     * @return The invalid usage command result.
     */
    public static CommandResult createUsageErrorResult(String errorMessage) {
        return new CommandResult(Optional.empty(), Optional.of(errorMessage), ResultType.INVALID_USAGE_ERROR);
    }

    /**
     * Creates a command result indicating an unexpected error occurred during command execution.
     *
     * @param errorMessage The error response explaining the unexpected error.
     * @return The unexpected error command result.
     */
    public static CommandResult createUnexpectedErrorResult(String errorMessage) {
        return new CommandResult(Optional.empty(), Optional.of(errorMessage), ResultType.UNEXPECTED_ERROR);
    }

    /**
     * Checks if the command result is successful.
     *
     * @return True if the command result is successful, false otherwise.
     */
    public boolean isSuccess() {
        return this.resultType() == ResultType.SUCCESS;
    }

    /**
     * Checks if the command result is an invalid usage error.
     *
     * @return True if the command result is an invalid usage error, false otherwise.
     */
    public boolean isUsageError() {
        return this.resultType() == ResultType.INVALID_USAGE_ERROR;
    }

    /**
     * Checks if the command result is an unexpected error.
     *
     * @return True if the command result is an unexpected error, false otherwise.
     */
    public boolean isUnexpectedError() {
        return this.resultType() == ResultType.UNEXPECTED_ERROR;
    }

    /**
     * Represents the possible types of result of a command function execution.
     */
    public enum ResultType {
        SUCCESS, INVALID_USAGE_ERROR, UNEXPECTED_ERROR
    }
}
