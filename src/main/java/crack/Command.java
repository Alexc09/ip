package crack;

/**
 * The commands Crack knows, each tied to the word the user types.
 */
public enum Command {
    /** Ends the session. */
    BYE("bye"),
    /** Prints every task. */
    LIST("list"),
    /** Marks a task done. */
    MARK("mark"),
    /** Marks a task not done. */
    UNMARK("unmark"),
    /** Removes a task. */
    DELETE("delete"),
    /** Adds a task with no date. */
    TODO("todo"),
    /** Adds a task due by a date. */
    DEADLINE("deadline"),
    /** Adds a task spanning two dates. */
    EVENT("event"),
    /** Prints whatever lands on one day. */
    ON("on"),

    /** Prints every task whose description contains a keyword. */
    FIND("find");

    private final String keyword;

    Command(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Returns the command matching the word the user typed.
     *
     * @param keyword The first word of the user's input.
     * @return The matching command.
     * @throws CrackException If no command uses that word.
     */
    public static Command fromKeyword(String keyword) throws CrackException {
        for (Command command : values()) {
            if (command.keyword.equals(keyword)) {
                return command;
            }
        }
        throw new CrackException("Nah bro, I got no clue what ts means.");
    }
}
