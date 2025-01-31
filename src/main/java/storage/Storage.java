package storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Storage<T extends Serializable> {
    private final String relativeFilePath;
    private final Class<T> type;

    public Storage(String relativeFilePath, Class<T> type) {
        this.relativeFilePath = relativeFilePath;
        this.type = type;
    }

    public void save(T item) throws StorageException {
        this.ensureFileExists();
        try (FileOutputStream fileOut = new FileOutputStream(this.relativeFilePath); ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            objectOut.writeObject(item);
        } catch (IOException e) {
            throw new StorageException(String.format("Error saving data to file: %s.", this.relativeFilePath));
        }
    }

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

    public void ensureFileExists() throws StorageException {
        File file = new File(this.relativeFilePath);
        try {
            if (file.exists()) {
            } else if (!file.createNewFile()) {
                throw new StorageException(String.format("Error creating file: %s.", this.relativeFilePath));
            }
        } catch (IOException e) {
            throw new StorageException(String.format("Error creating file: %s.", this.relativeFilePath), e);
        }
    }
}
