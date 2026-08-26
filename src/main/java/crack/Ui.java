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
     * Greets the user.
     */
    public void showWelcome() {
        System.out.println(DIVIDER);
        System.out.println(BANNER);
        System.out.println("Yo! Crack pulled up.");
        System.out.println("What we cooking today, gng?");
        System.out.println(DIVIDER);
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
        System.out.println(DIVIDER);
    }

    /**
     * Tells the user why something did not work.
     *
     * @param message What went wrong.
     */
    public void showError(String message) {
        System.out.println(message);
    }

    /**
     * Signs off.
     */
    public void showGoodbye() {
        System.out.println("Aight bet, I'm finna fade.");
    }

    /**
     * Confirms that a task was added.
     *
     * @param task The task that went in.
     * @param count How many tasks there are now.
     */
    public void showAdded(Task task, int count) {
        System.out.println("Bet, added ts to the list:");
        System.out.println("  " + task);
        showCount(count);
    }

    /**
     * Confirms that a task was removed.
     *
     * @param task The task that was dropped.
     * @param count How many tasks are left.
     */
    public void showRemoved(Task task, int count) {
        System.out.println("Aight, yeeted ts off the list:");
        System.out.println("  " + task);
        showCount(count);
    }

    /**
     * Confirms that a task is now done.
     *
     * @param task The task that was marked.
     */
    public void showMarked(Task task) {
        System.out.println("Ayo let's go, ts is done:");
        System.out.println("  " + task);
    }

    /**
     * Confirms that a task is back on the pile.
     *
     * @param task The task that was unmarked.
     */
    public void showUnmarked(Task task) {
        System.out.println("Aight, ts ain't done no more:");
        System.out.println("  " + task);
    }

    /**
     * Prints the whole list, numbered from one.
     *
     * @param tasks The list to print.
     */
    public void showList(TaskList tasks) {
        if (tasks.isEmpty()) {
            System.out.println("Your list is empty, you free rn.");
            return;
        }
        System.out.println("Here's what you got on deck:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
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
            System.out.println("Nothing on " + day + ", you free that day.");
            return;
        }
        System.out.println("Here's what you got on " + day + ":");
        for (Task task : matches) {
            System.out.println("  " + task);
        }
    }

    /**
     * Says how many tasks are left.
     *
     * @param count The number of tasks in the list.
     */
    private void showCount(int count) {
        System.out.println("You got " + count + (count == 1 ? " thing" : " things") + " lined up now.");
    }
}
