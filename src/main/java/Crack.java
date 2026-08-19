import java.util.Scanner;

/**
 * Entry point of the Crack chatbot.
 */
public class Crack {
    /** Line drawn between messages. */
    private static final String DIVIDER = "_".repeat(60);

    /** Cap on how many tasks we can hold. */
    private static final int MAX_TASKS = 100;

    private static final String BANNER = "  ____                _    \n"
            + " / ___|_ __ __ _  ___| | __\n"
            + "| |   | '__/ _` |/ __| |/ /\n"
            + "| |___| | | (_| | (__|   < \n"
            + " \\____|_|  \\__,_|\\___|_|\\_\\";

    private static final Task[] tasks = new Task[MAX_TASKS];
    private static int taskCount = 0;

    public static void main(String[] args) {
        System.out.println(DIVIDER);
        System.out.println(BANNER);
        System.out.println("Yo! Crack pulled up.");
        System.out.println("What we cooking today, gng?");
        System.out.println(DIVIDER);

        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        while (isRunning && scanner.hasNextLine()) {
            String input = scanner.nextLine().trim();
            System.out.println(DIVIDER);
            try {
                isRunning = handle(input);
            } catch (CrackException e) {
                System.out.println(e.getMessage());
            }
            System.out.println(DIVIDER);
        }
    }

    /** Runs one command. Returns false when it's time to stop. */
    private static boolean handle(String input) throws CrackException {
        String[] parts = input.split(" ", 2);
        String command = parts[0];
        String arguments = parts.length > 1 ? parts[1].trim() : "";

        switch (command) {
        case "bye":
            System.out.println("Aight bet, I'm finna fade.");
            return false;
        case "list":
            listTasks();
            return true;
        case "mark":
            markTask(parseIndex(arguments));
            return true;
        case "unmark":
            unmarkTask(parseIndex(arguments));
            return true;
        case "todo":
            addTask(new Todo(requireDescription(arguments, "todo")));
            return true;
        case "deadline":
            addTask(parseDeadline(arguments));
            return true;
        case "event":
            addTask(parseEvent(arguments));
            return true;
        default:
            throw new CrackException("Nah bro, I got no clue what ts means.");
        }
    }

    /** Rejects a blank description. */
    private static String requireDescription(String description, String taskType) throws CrackException {
        if (description.isEmpty()) {
            throw new CrackException("Nah gng, a " + taskType + " needs an actual description.");
        }
        return description;
    }

    /** Parses "description /by when". */
    private static Task parseDeadline(String arguments) throws CrackException {
        String[] parts = arguments.split(" /by ", 2);
        if (parts.length < 2 || parts[1].isBlank()) {
            throw new CrackException("A deadline needs a '/by', like: deadline return book /by Sunday");
        }
        return new Deadline(requireDescription(parts[0].trim(), "deadline"), parts[1].trim());
    }

    /** Parses "description /from start /to end". */
    private static Task parseEvent(String arguments) throws CrackException {
        String[] parts = arguments.split(" /from ", 2);
        if (parts.length < 2) {
            throw new CrackException("An event needs a '/from' and a '/to', "
                    + "like: event project meeting /from Mon 2pm /to 4pm");
        }
        String[] period = parts[1].split(" /to ", 2);
        if (period.length < 2 || period[0].isBlank() || period[1].isBlank()) {
            throw new CrackException("An event needs a '/from' and a '/to', "
                    + "like: event project meeting /from Mon 2pm /to 4pm");
        }
        return new Event(requireDescription(parts[0].trim(), "event"), period[0].trim(), period[1].trim());
    }

    /** Turns a task number into an array index. */
    private static int parseIndex(String arguments) throws CrackException {
        if (arguments.isEmpty()) {
            throw new CrackException("Which one tho? Gimme a task number.");
        }
        int position;
        try {
            position = Integer.parseInt(arguments);
        } catch (NumberFormatException e) {
            throw new CrackException("'" + arguments + "' ain't a number, gng.");
        }
        if (taskCount == 0) {
            throw new CrackException("Your list is empty, ain't nothing to point at.");
        }
        if (position < 1 || position > taskCount) {
            throw new CrackException("You only got " + taskCount + (taskCount == 1 ? " task" : " tasks")
                    + ", so " + position + " ain't it.");
        }
        return position - 1;
    }

    /** Adds a task and says so. */
    private static void addTask(Task task) throws CrackException {
        if (taskCount == MAX_TASKS) {
            throw new CrackException("List is maxed out at " + MAX_TASKS + ", can't fit no more.");
        }
        tasks[taskCount] = task;
        taskCount++;
        System.out.println("Bet, added ts to the list:");
        System.out.println("  " + task);
        System.out.println("You got " + taskCount + (taskCount == 1 ? " thing" : " things") + " lined up now.");
    }

    /** Prints the whole list. */
    private static void listTasks() {
        if (taskCount == 0) {
            System.out.println("Your list is empty, you free rn.");
            return;
        }
        System.out.println("Here's what you got on deck:");
        for (int i = 0; i < taskCount; i++) {
            System.out.println((i + 1) + "." + tasks[i]);
        }
    }

    /** Marks a task done. */
    private static void markTask(int index) {
        Task task = tasks[index];
        task.markAsDone();
        System.out.println("Ayo let's go, ts is done:");
        System.out.println("  " + task);
    }

    /** Marks a task not done. */
    private static void unmarkTask(int index) {
        Task task = tasks[index];
        task.markAsNotDone();
        System.out.println("Aight, ts ain't done no more:");
        System.out.println("  " + task);
    }
}
