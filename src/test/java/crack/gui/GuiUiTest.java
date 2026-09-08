package crack.gui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class GuiUiTest {
    @Test
    public void takeReply_plainOutput_notFlaggedAsError() {
        GuiUi ui = new GuiUi();
        ui.showGoodbye();

        GuiUi.Reply reply = ui.takeReply();
        assertEquals("Aight bet, I'm finna fade.", reply.text());
        assertFalse(reply.isError());
    }

    @Test
    public void takeReply_afterShowError_flaggedAsError() {
        GuiUi ui = new GuiUi();
        ui.showError("Idk what ts means, gng.");

        GuiUi.Reply reply = ui.takeReply();
        assertEquals("Idk what ts means, gng.", reply.text());
        assertTrue(reply.isError());
    }

    @Test
    public void takeReply_errorFollowedByNewTurn_flagResets() {
        GuiUi ui = new GuiUi();
        ui.showError("Nah, that ain't a number.");
        ui.takeReply();

        ui.showGreeting();
        assertFalse(ui.takeReply().isError());
    }

    @Test
    public void takeReply_calledTwice_secondComesBackEmpty() {
        GuiUi ui = new GuiUi();
        ui.showGoodbye();
        ui.takeReply();

        assertEquals("", ui.takeReply().text());
    }
}
