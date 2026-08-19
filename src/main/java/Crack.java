import java.util.Scanner;

/**
 * Entry point of the Crack chatbot.
 */
public class Crack {
    /** Horizontal rule used to separate the chatbot's messages from each other. */
    private static final String DIVIDER = "_".repeat(60);

    private static final String BANNER = "  ____                _    \n"
            + " / ___|_ __ __ _  ___| | __\n"
            + "| |   | '__/ _` |/ __| |/ /\n"
            + "| |___| | | (_| | (__|   < \n"
            + " \\____|_|  \\__,_|\\___|_|\\_\\";

    public static void main(String[] args) {
        System.out.println(DIVIDER);
        System.out.println(BANNER);
        System.out.println("Yo! Crack pulled up.");
        System.out.println("What we cooking today, gng?");
        System.out.println(DIVIDER);
        System.out.println("Aight bet, imma fade");
        System.out.println(DIVIDER);
    }
}
