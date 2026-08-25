/** Makes sense of what the user typed. */
public class Parser {
    /** One line of input, split into the command word and everything after it. */
    public record Parsed(Command command, String arguments) {
    }

    /** Splits a line into its command and arguments. */
    public static Parsed parse(String input) throws CrackException {
        String[] parts = input.split(" ", 2);
        Command command = Command.fromKeyword(parts[0]);
        String arguments = parts.length > 1 ? parts[1].trim() : "";
        return new Parsed(command, arguments);
    }

    /** Builds a todo from "description". */
    public static Task parseTodo(String arguments) throws CrackException {
        return new Todo(requireDescription(arguments, "todo"));
    }

    /** Builds a deadline from "description /by when". */
    public static Task parseDeadline(String arguments) throws CrackException {
        String[] parts = arguments.split(" /by ", 2);
        if (parts.length < 2 || parts[1].isBlank()) {
            throw new CrackException("A deadline needs a '/by', like: deadline return book /by 2/12/2020 1500");
        }
        return Deadline.of(requireDescription(parts[0].trim(), "deadline"), parts[1].trim());
    }

    /** Builds an event from "description /from start /to end". */
    public static Task parseEvent(String arguments) throws CrackException {
        String[] parts = arguments.split(" /from ", 2);
        if (parts.length < 2) {
            throw new CrackException(EVENT_FORMAT_HINT);
        }
        String[] period = parts[1].split(" /to ", 2);
        if (period.length < 2 || period[0].isBlank() || period[1].isBlank()) {
            throw new CrackException(EVENT_FORMAT_HINT);
        }
        return Event.of(requireDescription(parts[0].trim(), "event"), period[0].trim(), period[1].trim());
    }

    /** Turns a task number into a list index, checking it points at something. */
    public static int parseIndex(String arguments, int listSize) throws CrackException {
        if (arguments.isEmpty()) {
            throw new CrackException("Which one tho? Gimme a task number.");
        }
        int position;
        try {
            position = Integer.parseInt(arguments);
        } catch (NumberFormatException e) {
            throw new CrackException("'" + arguments + "' ain't a number, gng.");
        }
        if (listSize == 0) {
            throw new CrackException("Your list is empty, ain't nothing to point at.");
        }
        if (position < 1 || position > listSize) {
            throw new CrackException("You only got " + listSize + (listSize == 1 ? " task" : " tasks")
                    + ", so " + position + " ain't it.");
        }
        return position - 1;
    }

    /** Reads the day the user asked about. */
    public static TaskDate parseDate(String arguments) throws CrackException {
        if (arguments.isEmpty()) {
            throw new CrackException("On what day tho? Try: on 2/12/2020");
        }
        return TaskDate.parse(arguments);
    }

    private static final String EVENT_FORMAT_HINT = "An event needs a '/from' and a '/to', "
            + "like: event project meeting /from 2/12/2020 1400 /to 2/12/2020 1600";

    /** Rejects a blank description. */
    private static String requireDescription(String description, String taskType) throws CrackException {
        if (description.isEmpty()) {
            throw new CrackException("Nah gng, a " + taskType + " needs an actual description.");
        }
        return description;
    }
}
