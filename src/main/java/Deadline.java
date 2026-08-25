import java.time.LocalDate;

/** A task due by some date, and maybe a time of day too. */
public class Deadline extends Task {
    private final TaskDate by;

    private Deadline(String description, TaskDate by) {
        super(description);
        this.by = by;
    }

    /** Builds a deadline from a date written in any of the formats we accept. */
    public static Deadline of(String description, String by) throws CrackException {
        return new Deadline(description, TaskDate.parse(by));
    }

    @Override
    public boolean isOn(LocalDate date) {
        return by.toLocalDate().equals(date);
    }

    @Override
    public String toSaveFormat() {
        return "D | " + super.toSaveFormat() + " | " + by.toSaveFormat();
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}
