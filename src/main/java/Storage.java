import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/** Keeps the task list on disk between runs. */
public class Storage {
    private static final String FILE_PATH = "./data/data.txt";

    /** Loads the saved tasks, or an empty list if there's nothing saved yet. */
    public static ArrayList<Task> load() {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return tasks;
        }
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                Task task = parse(scanner.nextLine());
                if (task != null) {
                    tasks.add(task);
                }
            }
        } catch (IOException e) {
            System.out.println("Couldn't read your saved list, starting fresh.");
        }
        return tasks;
    }

    /** Writes the whole list out, replacing whatever was saved before. */
    public static void save(ArrayList<Task> tasks) {
        File file = new File(FILE_PATH);
        file.getParentFile().mkdirs();
        try (FileWriter writer = new FileWriter(file)) {
            for (Task task : tasks) {
                writer.write(task.toSaveFormat() + System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("Couldn't save your list, gng.");
        }
    }

    /** Rebuilds one task from its saved line, or null if the line is junk. */
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
