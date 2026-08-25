/** The commands Crack knows. */
public enum Command {
    BYE("bye"),
    LIST("list"),
    MARK("mark"),
    UNMARK("unmark"),
    DELETE("delete"),
    TODO("todo"),
    DEADLINE("deadline"),
    EVENT("event"),
    ON("on");

    private final String keyword;

    Command(String keyword) {
        this.keyword = keyword;
    }

    /** Finds the command matching what the user typed. */
    public static Command fromKeyword(String keyword) throws CrackException {
        for (Command command : values()) {
            if (command.keyword.equals(keyword)) {
                return command;
            }
        }
        throw new CrackException("Nah bro, I got no clue what ts means.");
    }
}
