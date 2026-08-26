package crack.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import crack.CrackException;

public class TaskDateTest {
    @Test
    public void parse_dateWithTime_showsBoth() throws CrackException {
        assertEquals("Dec 2 2020, 3:00 PM", TaskDate.parse("2/12/2020 1500").toString());
    }

    @Test
    public void parse_dateOnly_showsNoTime() throws CrackException {
        assertEquals("Oct 15 2019", TaskDate.parse("2019-10-15").toString());
    }

    @Test
    public void parse_slashesAndDashes_meanTheSameThing() throws CrackException {
        assertEquals(TaskDate.parse("2-12-2020 1500").toString(), TaskDate.parse("2/12/2020 1500").toString());
        assertEquals(TaskDate.parse("2020-12-02").toString(), TaskDate.parse("2020/12/02").toString());
    }

    @Test
    public void parse_dayFirstAndYearFirst_agreeOnTheDay() throws CrackException {
        assertEquals(TaskDate.parse("2019-10-15").toLocalDate(), TaskDate.parse("15/10/2019").toLocalDate());
    }

    @Test
    public void toSaveFormat_keepsTheTimeOnlyWhenOneWasGiven() throws CrackException {
        assertEquals("2020-12-02 1500", TaskDate.parse("2/12/2020 1500").toSaveFormat());
        assertEquals("2019-10-15", TaskDate.parse("15/10/2019").toSaveFormat());
    }

    @Test
    public void toLocalDate_dropsTheTime() throws CrackException {
        assertEquals(LocalDate.of(2020, 12, 2), TaskDate.parse("2/12/2020 1500").toLocalDate());
    }

    @Test
    public void formatDay_neverShowsATime() throws CrackException {
        assertEquals("Dec 2 2020", TaskDate.parse("2/12/2020 1500").formatDay());
    }

    @Test
    public void parse_nonsense_throws() {
        assertThrows(CrackException.class, () -> TaskDate.parse("Sunday"));
        assertThrows(CrackException.class, () -> TaskDate.parse("2019-13-45"));
        assertThrows(CrackException.class, () -> TaskDate.parse(""));
    }
}
