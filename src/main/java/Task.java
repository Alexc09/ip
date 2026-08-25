import java.time.LocalDate;

/** One task in the list. */
public class Task {
    protected final String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /** Marks it done. */
    public void markAsDone() {
        this.isDone = true;
    }

    /** Marks it not done. */
    public void markAsNotDone() {
        this.isDone = false;
    }

    /** Whether this task lands on the given day. Only dated tasks ever do. */
    public boolean isOn(LocalDate date) {
        return false;
    }

    /** How this task looks in the save file. */
    public String toSaveFormat() {
        return (isDone ? "1" : "0") + " | " + description;
    }

    /** "X" when done, blank otherwise. */
    private String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
