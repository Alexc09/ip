import java.util.ArrayList;
import java.util.Scanner;

/**
 * Entry point of the Crack chatbot.
 */
public class Crack {
    /** Line drawn between messages. */
    private static final String DIVIDER = "_".repeat(60);

    private static final String BANNER = "  ____                _    \n"
            + " / ___|_ __ __ _  ___| | __\n"
            + "| |   | '__/ _` |/ __| |/ /\n"
            + "| |___| | | (_| | (__|   < \n"
            + " \\____|_|  \\__,_|\\___|_|\\_\\";

    private static final ArrayList<Task> tasks = new ArrayList<>();

    public static void main(String[] args) {
        tasks.addAll(Storage.load());

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
        Command command = Command.fromKeyword(parts[0]);
        String arguments = parts.length > 1 ? parts[1].trim() : "";

        switch (command) {
        case BYE -> {
            System.out.println("Aight bet, I'm finna fade.");
            return false;
        }
        case LIST -> listTasks();
        case MARK -> markTask(parseIndex(arguments));
        case UNMARK -> unmarkTask(parseIndex(arguments));
        case DELETE -> deleteTask(parseIndex(arguments));
        case TODO -> addTask(new Todo(requireDescription(arguments, "todo")));
        case DEADLINE -> addTask(parseDeadline(arguments));
        case EVENT -> addTask(parseEvent(arguments));
        case ON -> listOn(arguments);
        }
        return true;
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
        return Deadline.of(requireDescription(parts[0].trim(), "deadline"), parts[1].trim());
    }

    /** Parses "description /from start /to end". */
    private static Task parseEvent(String arguments) throws CrackException {
        String[] parts = arguments.split(" /from ", 2);
        if (parts.length < 2) {
            throw new CrackException("An event needs a '/from' and a '/to', "
                    + "like: event project meeting /from 2/12/2020 1400 /to 2/12/2020 1600");
        }
        String[] period = parts[1].split(" /to ", 2);
        if (period.length < 2 || period[0].isBlank() || period[1].isBlank()) {
            throw new CrackException("An event needs a '/from' and a '/to', "
                    + "like: event project meeting /from 2/12/2020 1400 /to 2/12/2020 1600");
        }
        return Event.of(requireDescription(parts[0].trim(), "event"), period[0].trim(), period[1].trim());
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
        if (tasks.isEmpty()) {
            throw new CrackException("Your list is empty, ain't nothing to point at.");
        }
        if (position < 1 || position > tasks.size()) {
            throw new CrackException("You only got " + tasks.size() + (tasks.size() == 1 ? " task" : " tasks")
                    + ", so " + position + " ain't it.");
        }
        return position - 1;
    }

    /** Adds a task and says so. */
    private static void addTask(Task task) {
        tasks.add(task);
        Storage.save(tasks);
        System.out.println("Bet, added ts to the list:");
        System.out.println("  " + task);
        printCount();
    }

    /** Drops a task off the list. */
    private static void deleteTask(int index) {
        Task task = tasks.remove(index);
        Storage.save(tasks);
        System.out.println("Aight, yeeted ts off the list:");
        System.out.println("  " + task);
        printCount();
    }

    /** Says how many tasks are left. */
    private static void printCount() {
        int count = tasks.size();
        System.out.println("You got " + count + (count == 1 ? " thing" : " things") + " lined up now.");
    }

    /** Prints everything landing on one day. */
    private static void listOn(String arguments) throws CrackException {
        if (arguments.isEmpty()) {
            throw new CrackException("On what day tho? Try: on 2/12/2020");
        }
        TaskDate date = TaskDate.parse(arguments);
        ArrayList<Task> matches = new ArrayList<>();
        for (Task task : tasks) {
            if (task.isOn(date.toLocalDate())) {
                matches.add(task);
            }
        }
        if (matches.isEmpty()) {
            System.out.println("Nothing on " + date.dayDisplay() + ", you free that day.");
            return;
        }
        System.out.println("Here's what you got on " + date.dayDisplay() + ":");
        for (Task task : matches) {
            System.out.println("  " + task);
        }
    }

    /** Prints the whole list. */
    private static void listTasks() {
        if (tasks.isEmpty()) {
            System.out.println("Your list is empty, you free rn.");
            return;
        }
        System.out.println("Here's what you got on deck:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }

    /** Marks a task done. */
    private static void markTask(int index) {
        Task task = tasks.get(index);
        task.markAsDone();
        Storage.save(tasks);
        System.out.println("Ayo let's go, ts is done:");
        System.out.println("  " + task);
    }

    /** Marks a task not done. */
    private static void unmarkTask(int index) {
        Task task = tasks.get(index);
        task.markAsNotDone();
        Storage.save(tasks);
        System.out.println("Aight, ts ain't done no more:");
        System.out.println("  " + task);
    }
}
