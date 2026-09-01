package crack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import crack.task.Deadline;
import crack.task.Event;
import crack.task.Todo;

public class StorageTest {
    @TempDir
    private Path tempDir;

    @Test
    public void load_noSaveFileYet_comesBackEmpty() throws CrackException {
        Storage storage = new Storage(tempDir.resolve("data").resolve("data.txt").toString());
        assertTrue(storage.load().isEmpty());
    }

    @Test
    public void saveThenLoad_everyTaskTypeSurvives() throws CrackException {
        Path file = tempDir.resolve("data").resolve("data.txt");
        Storage storage = new Storage(file.toString());

        TaskList tasks = new TaskList();
        tasks.add(new Todo("buy milk"),
                Deadline.of("return book", "2/12/2020 1500"),
                Deadline.of("other thing", "2019-10-15"),
                Event.of("carnival", "1/12/2020 1400", "3/12/2020 1800"));
        tasks.get(0).markAsDone();
        storage.save(tasks);

        TaskList loaded = new Storage(file.toString()).load();
        assertEquals(4, loaded.size());
        assertEquals("[T][X] buy milk", loaded.get(0).toString());
        assertEquals("[D][ ] return book (by: Dec 2 2020, 3:00 PM)", loaded.get(1).toString());
        assertEquals("[D][ ] other thing (by: Oct 15 2019)", loaded.get(2).toString());
        assertEquals("[E][ ] carnival (from: Dec 1 2020, 2:00 PM to: Dec 3 2020, 6:00 PM)",
                loaded.get(3).toString());
    }

    @Test
    public void load_corruptedLines_skippedRatherThanCrashing() throws CrackException, IOException {
        Path file = tempDir.resolve("data.txt");
        Files.writeString(file, String.join(System.lineSeparator(),
                "T | 0 | keep me",
                "garbage line",
                "D | 9",
                "X | 0 | unknown type",
                "D | 0 | old style | Sunday",
                "E | 1 | party | 2020-12-05 2000 | 2020-12-05 2300"));

        TaskList loaded = new Storage(file.toString()).load();
        assertEquals(2, loaded.size());
        assertEquals("[T][ ] keep me", loaded.get(0).toString());
        assertEquals("[E][X] party (from: Dec 5 2020, 8:00 PM to: Dec 5 2020, 11:00 PM)",
                loaded.get(1).toString());
    }
}
