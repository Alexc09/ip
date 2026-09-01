package crack.gui;

import crack.Ui;

/**
 * Collects what Crack says instead of printing it, so the window can put the
 * same wording inside a dialog box.
 */
public class GuiUi extends Ui {
    private final StringBuilder reply = new StringBuilder();

    /**
     * Skips the divider, since every reply already sits in its own bubble.
     */
    @Override
    public void showLine() {
        // Nothing to draw: the window separates messages by putting them in separate bubbles.
    }

    /**
     * Returns everything said since the last call, then starts collecting afresh.
     *
     * @return The collected reply, with no trailing newline.
     */
    public String takeReply() {
        String collected = reply.toString();
        reply.setLength(0);
        return collected;
    }

    @Override
    protected void print(String line) {
        if (!reply.isEmpty()) {
            reply.append("\n");
        }
        reply.append(line);
    }
}
