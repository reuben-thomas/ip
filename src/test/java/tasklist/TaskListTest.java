package tasklist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TaskListTest {

    private TaskList taskList;
    private Task task1;
    private Task task2;
    private Task task3;

    @BeforeEach
    public void setUp() {
        taskList = new TaskList();
        task1 = new ToDoTask("Fix spaceship");
        task2 = new DeadlineTask("Communicate with earth", LocalDate.of(2024, 1, 1));
        task3 = new EventTask("Explore Miller's planet", LocalDate.of(2023, 1, 1), LocalDate.of(2025, 1, 2));
    }

    @Test
    public void testAddTasks() {
        Task[] tasks = new Task[]{this.task1, this.task2, this.task3};

        for (int i = 0; i < tasks.length; i++) {
            this.taskList.addTask(tasks[i]);
            assertEquals(i + 1, this.taskList.getLength(),
                    "Length of task list should be updated after adding new task.");
            assertEquals(this.taskList.getTask(this.taskList.getLength() - 1), tasks[i],
                    "Task should be added as last task in list.");
        }
    }

    @Test
    public void testEquals() {
        this.testAddTasks();

        TaskList taskListNew = new TaskList();
        taskListNew.addTask(this.task1);
        taskListNew.addTask(this.task2);
        taskListNew.addTask(this.task3);
        assertEquals(this.taskList, taskListNew,
                "Task list should be equal to another task list with same tasks in same order.");
    }

    @Test
    public void testDeleteTasks() {
        this.testAddTasks();

        this.taskList.deleteTask(1);
        assertEquals(2, this.taskList.getLength());
        assertEquals(this.taskList.getTask(0), this.task1);
        assertEquals(this.taskList.getTask(1), this.task3);

        this.taskList.deleteTask(0);
        assertEquals(1, this.taskList.getLength());
        assertEquals(this.taskList.getTask(0), this.task3);

        this.taskList.deleteTask(0);
        assertEquals(0, this.taskList.getLength());
    }

    @Test
    public void testTaskSetCompletion() {
        this.testAddTasks();
        for (int i = 0; i < this.taskList.getLength(); i++) {
            this.taskList.setTaskComplete(i);
            assertTrue(this.taskList.getTask(i).isCompleted(), "Task should be marked as complete.");
        }
        for (int i = 0; i < this.taskList.getLength(); i++) {
            this.taskList.setTaskIncomplete(i);
            assertFalse(this.taskList.getTask(i).isCompleted(), "Task should be marked as incomplete.");
        }
    }
}
