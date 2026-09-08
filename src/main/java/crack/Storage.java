package crack;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
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
                parse(scanner.nextLine()).ifPresent(tasks::add);
            }
        } catch (IOException e) {
            throw new CrackException("Couldn't read your saved list gng. Starting fresh.");
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
            throw new CrackException("Couldn't save your list gng, I'm finna crash out.");
        }
    }

    /**
     * Rebuilds one task from the line that was saved for it.
     *
     * @param line One line of the save file.
     * @return The task, or nothing if the line is junk we cannot make sense of.
     */
    private static Optional<Task> parse(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            return Optional.empty();
        }
        Optional<Task> task = buildTask(parts);
        if (parts[1].equals("1")) {
            task.ifPresent(Task::markAsDone);
        }
        return task;
    }

    /**
     * Builds the right kind of task for the type tag the line starts with.
     *
     * @param parts The line already split on its separator.
     * @return The task, or nothing if the tag is unknown or its fields are missing.
     */
    private static Optional<Task> buildTask(String[] parts) {
        try {
            return switch (parts[0]) {
                case "T" -> Optional.of(new Todo(parts[2]));
                case "D" -> parts.length < 4
                        ? Optional.empty()
                        : Optional.of(Deadline.of(parts[2], parts[3]));
                case "E" -> parts.length < 5
                        ? Optional.empty()
                        : Optional.of(Event.of(parts[2], parts[3], parts[4]));
                default -> Optional.empty();
            };
        } catch (CrackException e) {
            return Optional.empty();
        }
    }
}
