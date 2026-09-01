package crack;

import java.util.List;
import java.util.Scanner;

import crack.task.Task;

/**
 * Everything Crack says to the user, and how it hears back.
 * Keeping the wording in one place means the rest of the code never prints anything itself.
 */
public class Ui {
    /** Line drawn between messages. */
    private static final String DIVIDER = "_".repeat(60);

    private static final String BANNER = "  ____                _    \n"
            + " / ___|_ __ __ _  ___| | __\n"
            + "| |   | '__/ _` |/ __| |/ /\n"
            + "| |___| | | (_| | (__|   < \n"
            + " \\____|_|  \\__,_|\\___|_|\\_\\";

    private final Scanner scanner = new Scanner(System.in);

    /**
     * Prints one line of output.
     * The GUI overrides this to collect the same wording instead of printing it.
     *
     * @param line The line to show.
     */
    protected void print(String line) {
        System.out.println(line);
    }

    /**
     * Greets the user, banner and all.
     */
    public void showWelcome() {
        showLine();
        print(BANNER);
        showGreeting();
        showLine();
    }

    /**
     * Says hello without the banner, which needs a fixed width font to line up.
     */
    public void showGreeting() {
        print("Yo! Crack pulled up.");
        print("What we cooking today, gng?");
    }

    /**
     * Returns whether there is another line of input waiting.
     */
    public boolean hasNextCommand() {
        return scanner.hasNextLine();
    }

    /**
     * Reads one line of input, trimmed.
     */
    public String readCommand() {
        return scanner.nextLine().trim();
    }

    /**
     * Draws the line that separates one message from the next.
     */
    public void showLine() {
        print(DIVIDER);
    }

    /**
     * Tells the user why something did not work.
     *
     * @param message What went wrong.
     */
    public void showError(String message) {
        print(message);
    }

    /**
     * Signs off.
     */
    public void showGoodbye() {
        print("Aight bet, I'm finna fade.");
    }

    /**
     * Confirms that a task was added.
     *
     * @param task The task that went in.
     * @param count How many tasks there are now.
     */
    public void showAdded(Task task, int count) {
        print("Bet, added ts to the list:");
        print("  " + task);
        showCount(count);
    }

    /**
     * Confirms that a task was removed.
     *
     * @param task The task that was dropped.
     * @param count How many tasks are left.
     */
    public void showRemoved(Task task, int count) {
        print("Aight, yeeted ts off the list:");
        print("  " + task);
        showCount(count);
    }

    /**
     * Confirms that a task is now done.
     *
     * @param task The task that was marked.
     */
    public void showMarked(Task task) {
        print("Ayo let's go, ts is done:");
        print("  " + task);
    }

    /**
     * Confirms that a task is back on the pile.
     *
     * @param task The task that was unmarked.
     */
    public void showUnmarked(Task task) {
        print("Aight, ts ain't done no more:");
        print("  " + task);
    }

    /**
     * Prints the whole list, numbered from one.
     *
     * @param tasks The list to print.
     */
    public void showList(TaskList tasks) {
        if (tasks.isEmpty()) {
            print("Your list is empty, you free rn.");
            return;
        }
        print("Here's what you got on deck:");
        for (int i = 0; i < tasks.size(); i++) {
            print((i + 1) + "." + tasks.get(i));
        }
    }

    /**
     * Prints whatever lands on one day.
     *
     * @param day The day being asked about, already written out for display.
     * @param matches The tasks falling on that day, possibly none.
     */
    public void showTasksOn(String day, List<Task> matches) {
        if (matches.isEmpty()) {
            print("Nothing on " + day + ", you free that day.");
            return;
        }
        print("Here's what you got on " + day + ":");
        for (Task task : matches) {
            print("  " + task);
        }
    }

    /**
     * Prints the tasks that matched a search, numbered from one.
     *
     * @param matches The tasks whose descriptions contained the keyword.
     */
    public void showFound(List<Task> matches) {
        if (matches.isEmpty()) {
            print("Ain't nothing matching that, gng.");
            return;
        }
        print("Here's what matched:");
        for (int i = 0; i < matches.size(); i++) {
            print((i + 1) + "." + matches.get(i));
        }
    }

    /**
     * Says how many tasks are left.
     *
     * @param count The number of tasks in the list.
     */
    private void showCount(int count) {
        print("You got " + count + (count == 1 ? " thing" : " things") + " lined up now.");
    }
}
