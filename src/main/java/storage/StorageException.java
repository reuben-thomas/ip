package storage;

import java.io.IOException;

/**
 * Represents an exception that occurs during storage operations.
 */
public class StorageException extends IOException {
    /**
     * Constructor for StorageException class.
     *
     * @param message The message to be displayed when the exception is thrown.
     */
    public StorageException(String message) {
        super(message);
    }

    /**
     * Constructor for StorageException class.
     *
     * @param message The message to be displayed when the exception is thrown.
     * @param cause   The cause of the exception.
     */
    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
