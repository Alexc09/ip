package crack;

import crack.task.Task;
import java.util.List;
import java.util.Scanner;

/** Everything Crack says to the user, and how it hears back. */
public class Ui {
    /** Line drawn between messages. */
    private static final String DIVIDER = "_".repeat(60);

    private static final String BANNER = "  ____                _    \n"
            + " / ___|_ __ __ _  ___| | __\n"
            + "| |   | '__/ _` |/ __| |/ /\n"
            + "| |___| | | (_| | (__|   < \n"
            + " \\____|_|  \\__,_|\\___|_|\\_\\";

    private final Scanner scanner = new Scanner(System.in);

    /** Greets the user. */
    public void showWelcome() {
        System.out.println(DIVIDER);
        System.out.println(BANNER);
        System.out.println("Yo! Crack pulled up.");
        System.out.println("What we cooking today, gng?");
        System.out.println(DIVIDER);
    }

    /** Whether there's another line of input waiting. */
    public boolean hasNextCommand() {
        return scanner.hasNextLine();
    }

    /** Reads one line of input. */
    public String readCommand() {
        return scanner.nextLine().trim();
    }

    /** Draws the line between messages. */
    public void showLine() {
        System.out.println(DIVIDER);
    }

    /** Says why something didn't work. */
    public void showError(String message) {
        System.out.println(message);
    }

    /** Signs off. */
    public void showGoodbye() {
        System.out.println("Aight bet, I'm finna fade.");
    }

    /** Confirms a new task. */
    public void showAdded(Task task, int count) {
        System.out.println("Bet, added ts to the list:");
        System.out.println("  " + task);
        showCount(count);
    }

    /** Confirms a task is gone. */
    public void showRemoved(Task task, int count) {
        System.out.println("Aight, yeeted ts off the list:");
        System.out.println("  " + task);
        showCount(count);
    }

    /** Confirms a task is done. */
    public void showMarked(Task task) {
        System.out.println("Ayo let's go, ts is done:");
        System.out.println("  " + task);
    }

    /** Confirms a task is back on the pile. */
    public void showUnmarked(Task task) {
        System.out.println("Aight, ts ain't done no more:");
        System.out.println("  " + task);
    }

    /** Prints the whole list, numbered. */
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

    /** Prints whatever lands on one day. */
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

    /** Says how many tasks are left. */
    private void showCount(int count) {
        System.out.println("You got " + count + (count == 1 ? " thing" : " things") + " lined up now.");
    }
}
