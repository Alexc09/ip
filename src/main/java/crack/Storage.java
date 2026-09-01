package crack;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import crack.task.Deadline;
import crack.task.Event;
import crack.task.Task;
import crack.task.Todo;

/**
 * Keeps the task list on disk so it survives between runs.
 * Each task is one line, with its fields separated by " | ".
 */
public class Storage {
    private final String filePath;

    /**
     * Points storage at a save file.
     * The file and its folder are only created once there is something to save.
     *
     * @param filePath Where the save file lives.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads the saved tasks.
     * Lines that cannot be read are skipped rather than treated as failures,
     * so one bad line does not cost the user the whole list.
     *
     * @return The saved tasks, or an empty list if nothing has been saved yet.
     * @throws CrackException If the file exists but cannot be read.
     */
    public TaskList load() throws CrackException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            return new TaskList(tasks);
        }
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                Task task = parse(scanner.nextLine());
                if (task != null) {
                    tasks.add(task);
                }
            }
        } catch (IOException e) {
            throw new CrackException("Couldn't read your saved list, starting fresh.");
        }
        return new TaskList(tasks);
    }

    /**
     * Writes the whole list out, replacing whatever was saved before.
     *
     * @param tasks The tasks to save.
     * @throws CrackException If the file cannot be written.
     */
    public void save(TaskList tasks) throws CrackException {
        File file = new File(filePath);
        file.getParentFile().mkdirs();
        try (FileWriter writer = new FileWriter(file)) {
            for (Task task : tasks.getTasks()) {
                writer.write(task.toSaveFormat() + System.lineSeparator());
            }
        } catch (IOException e) {
            throw new CrackException("Couldn't save your list, gng.");
        }
    }

    /**
     * Rebuilds one task from the line that was saved for it.
     *
     * @param line One line of the save file.
     * @return The task, or null if the line is junk we cannot make sense of.
     */
    private static Task parse(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            return null;
        }
        Task task;
        try {
            task = switch (parts[0]) {
                case "T" -> new Todo(parts[2]);
                case "D" -> parts.length < 4 ? null : Deadline.of(parts[2], parts[3]);
                case "E" -> parts.length < 5 ? null : Event.of(parts[2], parts[3], parts[4]);
                default -> null;
            };
        } catch (CrackException e) {
            return null;
        }
        if (task != null && parts[1].equals("1")) {
            task.markAsDone();
        }
        return task;
    }
}
