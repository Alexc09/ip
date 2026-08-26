package crack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import crack.task.Deadline;
import crack.task.Event;
import crack.task.Task;
import crack.task.Todo;
import java.time.LocalDate;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

public class TaskListTest {
    @Test
    public void newList_startsEmpty() {
        assertTrue(new TaskList().isEmpty());
        assertEquals(0, new TaskList().size());
    }

    @Test
    public void addThenRemove_handsBackWhatWasRemoved() {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("buy milk"));
        tasks.add(new Todo("walk dog"));
        assertEquals(2, tasks.size());

        Task removed = tasks.remove(0);
        assertEquals("[T][ ] buy milk", removed.toString());
        assertEquals(1, tasks.size());
        assertEquals("[T][ ] walk dog", tasks.get(0).toString());
    }

    @Test
    public void onDate_keepsOnlyWhatLandsThatDay() throws CrackException {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("buy milk"));
        tasks.add(Deadline.of("return book", "2/12/2020 1500"));
        tasks.add(Event.of("carnival", "1/12/2020 1400", "3/12/2020 1800"));
        tasks.add(Deadline.of("other thing", "2019-10-15"));

        ArrayList<Task> matches = tasks.onDate(LocalDate.of(2020, 12, 2));
        assertEquals(2, matches.size());
        assertEquals("[D][ ] return book (by: Dec 2 2020, 3:00 PM)", matches.get(0).toString());
        assertEquals("[E][ ] carnival (from: Dec 1 2020, 2:00 PM to: Dec 3 2020, 6:00 PM)",
                matches.get(1).toString());
    }

    @Test
    public void onDate_nothingThatDay_comesBackEmpty() throws CrackException {
        TaskList tasks = new TaskList();
        tasks.add(Deadline.of("return book", "2/12/2020"));
        assertTrue(tasks.onDate(LocalDate.of(2020, 12, 4)).isEmpty());
    }
}
