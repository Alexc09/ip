package crack;

import crack.task.Task;
import java.time.LocalDate;
import java.util.ArrayList;

/** The tasks Crack is keeping track of. */
public class TaskList {
    private final ArrayList<Task> tasks;

    /** Starts an empty list. */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /** Starts from tasks that were already loaded. */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void add(Task task) {
        tasks.add(task);
    }

    /** Removes the task at the given index and hands it back. */
    public Task remove(int index) {
        return tasks.remove(index);
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    public int size() {
        return tasks.size();
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /** Every task, in order. */
    public ArrayList<Task> asArrayList() {
        return tasks;
    }

    /** Just the tasks landing on the given day. */
    public ArrayList<Task> onDate(LocalDate date) {
        ArrayList<Task> matches = new ArrayList<>();
        for (Task task : tasks) {
            if (task.isOn(date)) {
                matches.add(task);
            }
        }
        return matches;
    }
}
