package crack;

import java.util.Arrays;
import java.util.List;

/**
 * The commands Crack knows, each tied to the words the user can type for it.
 * Most commands answer to a short alias as well as their full name.
 */
public enum Command {
    /** Ends the session. */
    BYE("bye"),
    /** Prints every task. */
    LIST("list", "ls"),
    /** Marks a task done. */
    MARK("mark", "m"),
    /** Marks a task not done. */
    UNMARK("unmark", "um"),
    /** Removes a task. */
    DELETE("delete", "rm", "del"),
    /** Adds a task with no date. */
    TODO("todo", "t"),
    /** Adds a task due by a date. */
    DEADLINE("deadline", "d"),
    /** Adds a task spanning two dates. */
    EVENT("event", "e"),
    /** Prints whatever lands on one day. */
    ON("on"),

    /** Prints every task whose description contains a keyword. */
    FIND("find", "f");

    private final List<String> keywords;

    Command(String... keywords) {
        this.keywords = List.of(keywords);
    }

    /**
     * Returns the command matching the word the user typed.
     * A command answers to its full name and to any alias listed against it.
     *
     * @param keyword The first word of the user's input.
     * @return The matching command.
     * @throws CrackException If no command uses that word.
     */
    public static Command fromKeyword(String keyword) throws CrackException {
        return Arrays.stream(values())
                .filter(command -> command.keywords.contains(keyword))
                .findFirst()
                .orElseThrow(() -> new CrackException("Nah bro, I got no clue what ts means."));
    }

    /**
     * Returns every word that calls up this command, full name first.
     */
    public List<String> getKeywords() {
        return keywords;
    }
}
