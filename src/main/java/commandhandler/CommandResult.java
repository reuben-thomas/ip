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
    public CommandResult {
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

    }

    /**
     * Returns a newly created successful command result with the given response.
     *
     * @param response The successful response message.
     * @return The successful command result.
     */
    public static CommandResult createSuccessResult(String response) {
        return new CommandResult(Optional.of(response), Optional.empty(), ResultType.SUCCESS);
    }

    /**
     * Returns a new command result associated with invalid usage error.
     *
     * @param errorMessage The error message explaining the invalid usage.
     * @return The invalid usage command result.
     */
    public static CommandResult createUsageErrorResult(String errorMessage) {
        return new CommandResult(Optional.empty(), Optional.of(errorMessage), ResultType.INVALID_USAGE_ERROR);
    }

    /**
     * Returns a new command result associated with an unexpected error during execution.
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
