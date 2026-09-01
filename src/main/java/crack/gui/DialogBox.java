package crack.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * One message in the chat: the user's on the right, Crack's on the left.
 */
public class DialogBox extends HBox {
    private static final double MAX_TEXT_WIDTH = 300;
    private static final String USER_STYLE =
            "-fx-background-color: #2f6feb; -fx-text-fill: white; -fx-background-radius: 12; -fx-padding: 8;";
    private static final String CRACK_STYLE =
            "-fx-background-color: #e8eaed; -fx-text-fill: #1b1b1b; -fx-background-radius: 12; -fx-padding: 8;";

    private DialogBox(String text, String style, Pos alignment) {
        Label message = new Label(text);
        message.setWrapText(true);
        message.setMaxWidth(MAX_TEXT_WIDTH);
        message.setStyle(style);

        setAlignment(alignment);
        setPadding(new Insets(4, 8, 4, 8));
        getChildren().add(message);
    }

    /**
     * Returns a box holding something the user typed.
     *
     * @param text The line the user sent.
     * @return The box to add to the chat.
     */
    public static DialogBox forUser(String text) {
        return new DialogBox(text, USER_STYLE, Pos.TOP_RIGHT);
    }

    /**
     * Returns a box holding something Crack said back.
     *
     * @param text The reply to show.
     * @return The box to add to the chat.
     */
    public static DialogBox forCrack(String text) {
        return new DialogBox(text, CRACK_STYLE, Pos.TOP_LEFT);
    }
}
