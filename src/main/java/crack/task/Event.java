package crack.task;

import crack.CrackException;
import java.time.LocalDate;

/** A task that runs from one date to another. */
public class Event extends Task {
    private final TaskDate from;
    private final TaskDate to;

    private Event(String description, TaskDate from, TaskDate to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /** Builds an event from dates written in any of the formats we accept. */
    public static Event of(String description, String from, String to) throws CrackException {
        return new Event(description, TaskDate.parse(from), TaskDate.parse(to));
    }

    @Override
    public boolean isOn(LocalDate date) {
        return !date.isBefore(from.toLocalDate()) && !date.isAfter(to.toLocalDate());
    }

    @Override
    public String toSaveFormat() {
        return "E | " + super.toSaveFormat() + " | " + from.toSaveFormat() + " | " + to.toSaveFormat();
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
