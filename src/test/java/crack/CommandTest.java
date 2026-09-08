package crack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class CommandTest {
    @Test
    public void fromKeyword_fullName_findsTheCommand() throws CrackException {
        assertEquals(Command.TODO, Command.fromKeyword("todo"));
        assertEquals(Command.DELETE, Command.fromKeyword("delete"));
        assertEquals(Command.BYE, Command.fromKeyword("bye"));
    }

    @Test
    public void fromKeyword_alias_findsTheSameCommand() throws CrackException {
        assertEquals(Command.TODO, Command.fromKeyword("t"));
        assertEquals(Command.DEADLINE, Command.fromKeyword("d"));
        assertEquals(Command.EVENT, Command.fromKeyword("e"));
        assertEquals(Command.LIST, Command.fromKeyword("ls"));
        assertEquals(Command.MARK, Command.fromKeyword("m"));
        assertEquals(Command.UNMARK, Command.fromKeyword("um"));
        assertEquals(Command.DELETE, Command.fromKeyword("rm"));
        assertEquals(Command.DELETE, Command.fromKeyword("del"));
        assertEquals(Command.FIND, Command.fromKeyword("f"));
    }

    @Test
    public void fromKeyword_everyKeywordACommandClaims_resolvesBackToIt() throws CrackException {
        for (Command command : Command.values()) {
            for (String keyword : command.getKeywords()) {
                assertEquals(command, Command.fromKeyword(keyword));
            }
        }
    }

    @Test
    public void getKeywords_noTwoCommandsClaimTheSameWord() {
        List<String> seen = new ArrayList<>();
        for (Command command : Command.values()) {
            for (String keyword : command.getKeywords()) {
                assertFalse(seen.contains(keyword), "two commands answer to '" + keyword + "'");
                seen.add(keyword);
            }
        }
    }

    @Test
    public void fromKeyword_unknownWord_throws() {
        assertThrows(CrackException.class, () -> Command.fromKeyword("blah"));
        assertThrows(CrackException.class, () -> Command.fromKeyword(""));
    }

    @Test
    public void fromKeyword_wrongCase_throws() {
        assertThrows(CrackException.class, () -> Command.fromKeyword("T"));
        assertThrows(CrackException.class, () -> Command.fromKeyword("Todo"));
    }
}
