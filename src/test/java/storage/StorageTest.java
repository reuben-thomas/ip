package storage;

import tasklist.DeadlineTask;
import tasklist.EventTask;
import tasklist.TaskList;
import tasklist.ToDoTask;

import java.io.File;
import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class StorageTest {
    private static final String TEST_FILE_RELATIVE_PATH = "test_file.txt";

    private TaskList getSampleTaskList() {
        TaskList taskList = new TaskList();
        taskList.addTask(new ToDoTask("Fix spaceship"));
        taskList.addTask(new DeadlineTask("Communicate with earth", LocalDate.of(2024, 1, 1)));
        taskList.addTask(new EventTask("Explore Miller's planet", LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 2)));

        return taskList;
    }

    @AfterEach
    void deleteTestFile() {
        File file = new File(StorageTest.TEST_FILE_RELATIVE_PATH);
        if (!file.delete()) {
            fail("Test file could not be deleted.");
        }
    }

    @Test
    public void testEnsureFileExists() {
        // Ensure file created without exception if it does not exist
        Storage<TaskList> taskListStorage = new Storage<>(StorageTest.TEST_FILE_RELATIVE_PATH, TaskList.class);
        File file = new File(StorageTest.TEST_FILE_RELATIVE_PATH);
        assertDoesNotThrow(taskListStorage::ensureFileExists);
        assertTrue(file.exists(), "File should be created if it does not exist.");

        // Ensure file left untouched if it already exists
        assertDoesNotThrow(taskListStorage::ensureFileExists);
        assertTrue(file.exists(), "File should not be modified if it already exists.");
    }

    @Test
    public void testLoadInvalidType() {
        Storage<String> stringStorage = new Storage<>(StorageTest.TEST_FILE_RELATIVE_PATH, String.class);
        Storage<TaskList> taskListStorage = new Storage<>(StorageTest.TEST_FILE_RELATIVE_PATH, TaskList.class);
        assertDoesNotThrow(() -> stringStorage.save("Hello world!"), "String should be saved without exception.");

        try {
            taskListStorage.load();
            fail();
        } catch (StorageException e) {
            assertEquals(String.format("Error loading data from file: %s.", StorageTest.TEST_FILE_RELATIVE_PATH), e.getMessage(), "Loading a file with an invalid type should throw an exception.");
        }
    }

    @Test
    public void testSaveAndLoad() {
        Storage<TaskList> taskListStorage = new Storage<>(StorageTest.TEST_FILE_RELATIVE_PATH, TaskList.class);
        TaskList savedTaskList = this.getSampleTaskList();

        assertDoesNotThrow(() -> taskListStorage.save(savedTaskList));
        assertDoesNotThrow(() -> {
            TaskList loadedTaskList = taskListStorage.load();
            assertEquals(savedTaskList, loadedTaskList, "Task list loaded from file should be logically equivalent to task list saved to the same file.");
        });
    }
}
