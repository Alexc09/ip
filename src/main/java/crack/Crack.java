package crack;

import crack.task.Task;
import crack.task.TaskDate;

/**
 * Entry point of the Crack chatbot.
 */
public class Crack {
    private static final String SAVE_PATH = "./data/data.txt";

    private final Ui ui;
    private final Storage storage;
    private final TaskList tasks;

    /** Wires up the chatbot against the given save file. */
    public Crack(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        this.tasks = loadTasks();
    }

    public static void main(String[] args) {
        new Crack(SAVE_PATH).run();
    }

    /** Greets the user, then handles commands until they say bye. */
    public void run() {
        ui.showWelcome();
        boolean isRunning = true;
        while (isRunning && ui.hasNextCommand()) {
            String input = ui.readCommand();
            ui.showLine();
            try {
                isRunning = handle(input);
            } catch (CrackException e) {
                ui.showError(e.getMessage());
            }
            ui.showLine();
        }
    }

    /** Runs one command. Returns false when it's time to stop. */
    private boolean handle(String input) throws CrackException {
        Parser.Parsed parsed = Parser.parse(input);
        String arguments = parsed.arguments();

        switch (parsed.command()) {
        case BYE -> {
            ui.showGoodbye();
            return false;
        }
        case LIST -> ui.showList(tasks);
        case MARK -> markTask(Parser.parseIndex(arguments, tasks.size()));
        case UNMARK -> unmarkTask(Parser.parseIndex(arguments, tasks.size()));
        case DELETE -> deleteTask(Parser.parseIndex(arguments, tasks.size()));
        case TODO -> addTask(Parser.parseTodo(arguments));
        case DEADLINE -> addTask(Parser.parseDeadline(arguments));
        case EVENT -> addTask(Parser.parseEvent(arguments));
        case ON -> listOn(Parser.parseDate(arguments));
        }
        return true;
    }

    /** Loads the saved tasks, falling back to an empty list if the file won't read. */
    private TaskList loadTasks() {
        try {
            return storage.load();
        } catch (CrackException e) {
            ui.showError(e.getMessage());
            return new TaskList();
        }
    }

    /** Writes the list out, grumbling if it can't. */
    private void save() {
        try {
            storage.save(tasks);
        } catch (CrackException e) {
            ui.showError(e.getMessage());
        }
    }

    /** Adds a task and says so. */
    private void addTask(Task task) {
        tasks.add(task);
        save();
        ui.showAdded(task, tasks.size());
    }

    /** Drops a task off the list. */
    private void deleteTask(int index) {
        Task task = tasks.remove(index);
        save();
        ui.showRemoved(task, tasks.size());
    }

    /** Marks a task done. */
    private void markTask(int index) {
        Task task = tasks.get(index);
        task.markAsDone();
        save();
        ui.showMarked(task);
    }

    /** Marks a task not done. */
    private void unmarkTask(int index) {
        Task task = tasks.get(index);
        task.markAsNotDone();
        save();
        ui.showUnmarked(task);
    }

    /** Prints everything landing on one day. */
    private void listOn(TaskDate date) {
        ui.showTasksOn(date.dayDisplay(), tasks.onDate(date.toLocalDate()));
    }
}
