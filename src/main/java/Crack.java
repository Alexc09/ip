import java.util.Scanner;

/**
 * Entry point of the Crack chatbot.
 */
public class Crack {
    /** Horizontal rule used to separate the chatbot's messages from each other. */
    private static final String DIVIDER = "_".repeat(60);

    /** Maximum number of tasks the chatbot can hold. */
    private static final int MAX_TASKS = 100;

    private static final String BANNER = "  ____                _    \n"
            + " / ___|_ __ __ _  ___| | __\n"
            + "| |   | '__/ _` |/ __| |/ /\n"
            + "| |___| | | (_| | (__|   < \n"
            + " \\____|_|  \\__,_|\\___|_|\\_\\";

    private static final String[] tasks = new String[MAX_TASKS];
    private static int taskCount = 0;

    public static void main(String[] args) {
        System.out.println(DIVIDER);
        System.out.println(BANNER);
        System.out.println("Yo! Crack pulled up.");
        System.out.println("What we cooking today, gng?");
        System.out.println(DIVIDER);

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine().trim();
            System.out.println(DIVIDER);
            if (input.equals("bye")) {
                System.out.println("Aight bet, I'm finna fade.");
                System.out.println(DIVIDER);
                break;
            } else if (input.equals("list")) {
                listTasks();
            } else {
                addTask(input);
            }
            System.out.println(DIVIDER);
        }
    }

    /** Stores the given task and confirms it to the user. */
    private static void addTask(String task) {
        tasks[taskCount] = task;
        taskCount++;
        System.out.println("bet, added: " + task);
    }

    /** Prints every stored task, numbered from 1. */
    private static void listTasks() {
        for (int i = 0; i < taskCount; i++) {
            System.out.println((i + 1) + ". " + tasks[i]);
        }
    }
}
