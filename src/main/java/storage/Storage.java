package storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Represents a storage utility that can save and load serializable objects to and from a file.
 *
 * @param <T> The type of the object to be stored in the file.
 */
public class Storage<T extends Serializable> {
    private final String relativeFilePath;
    private final Class<T> type;

    /**
     * Constructor for Storage class.
     *
     * @param relativeFilePath The relative file path from the project root directory.
     * @param type             The type of the object to be stored in the file.
     */
    public Storage(String relativeFilePath, Class<T> type) {
        this.relativeFilePath = relativeFilePath;
        this.type = type;
    }

    /**
     * Saves an object to the file.
     *
     * @param item The object to be saved.
     * @throws StorageException If there is an error saving the object to the file.
     */
    public void save(T item) throws StorageException {
        this.ensureFileExists();
        try (FileOutputStream fileOut = new FileOutputStream(this.relativeFilePath); ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            objectOut.writeObject(item);
        } catch (IOException e) {
            throw new StorageException(String.format("Error saving data to file: %s.", this.relativeFilePath));
        }
    }

    /**
     * Loads an object from the file.
     *
     * @return The object loaded from the file.
     * @throws StorageException If there is an error loading the object from the file.
     */
    public T load() throws StorageException {
        this.ensureFileExists();
        try (FileInputStream fileIn = new FileInputStream(this.relativeFilePath); ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
            Object object = objectIn.readObject();
            if (this.type.isInstance(object)) {
                return this.type.cast(object);
            } else {
                throw new StorageException(String.format("Error loading data from file: %s.", this.relativeFilePath));
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new StorageException(String.format("Error loading data from file: %s.", this.relativeFilePath), e);
        }
    }

    /**
     * Ensures that the file exists, creating it if it does not exist.
     *
     * @throws StorageException If there is an error creating the file.
     */
    public void ensureFileExists() throws StorageException {
        File file = new File(this.relativeFilePath);
        try {
            if (file.exists()) {
                return;
            } else if (!file.createNewFile()) {
                throw new StorageException(String.format("Error creating file: %s.", this.relativeFilePath));
            }
        } catch (IOException e) {
            throw new StorageException(String.format("Error creating file: %s.", this.relativeFilePath), e);
        }
    }
}
