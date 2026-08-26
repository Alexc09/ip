package crack.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import crack.CrackException;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

public class TaskTest {
    private static final LocalDate DEC_1 = LocalDate.of(2020, 12, 1);
    private static final LocalDate DEC_2 = LocalDate.of(2020, 12, 2);
    private static final LocalDate DEC_3 = LocalDate.of(2020, 12, 3);
    private static final LocalDate DEC_4 = LocalDate.of(2020, 12, 4);

    @Test
    public void toString_showsTypeAndStatus() {
        Todo todo = new Todo("buy milk");
        assertEquals("[T][ ] buy milk", todo.toString());
        todo.markAsDone();
        assertEquals("[T][X] buy milk", todo.toString());
        todo.markAsNotDone();
        assertEquals("[T][ ] buy milk", todo.toString());
    }

    @Test
    public void toSaveFormat_writesEachTypeItsOwnWay() throws CrackException {
        assertEquals("T | 0 | buy milk", new Todo("buy milk").toSaveFormat());
        assertEquals("D | 0 | return book | 2020-12-02 1500",
                Deadline.of("return book", "2/12/2020 1500").toSaveFormat());
        assertEquals("E | 0 | carnival | 2020-12-01 1400 | 2020-12-03 1800",
                Event.of("carnival", "1/12/2020 1400", "3/12/2020 1800").toSaveFormat());
    }

    @Test
    public void isOn_todo_neverMatches() {
        assertFalse(new Todo("buy milk").isOn(DEC_2));
    }

    @Test
    public void isOn_deadline_matchesItsOwnDayOnly() throws CrackException {
        Deadline deadline = Deadline.of("return book", "2/12/2020 1500");
        assertTrue(deadline.isOn(DEC_2));
        assertFalse(deadline.isOn(DEC_1));
        assertFalse(deadline.isOn(DEC_3));
    }

    @Test
    public void isOn_event_coversEveryDayItSpans() throws CrackException {
        Event event = Event.of("carnival", "1/12/2020 1400", "3/12/2020 1800");
        assertTrue(event.isOn(DEC_1));
        assertTrue(event.isOn(DEC_2));
        assertTrue(event.isOn(DEC_3));
        assertFalse(event.isOn(DEC_4));
    }
}
