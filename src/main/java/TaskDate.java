import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/** A date, plus a time of day if the user gave one. */
public class TaskDate {
    /** Date-and-time shapes we accept from the user, e.g. 2/12/2020 1500. */
    private static final String[] DATE_TIME_FORMATS = {"yyyy-MM-dd HHmm", "d-M-yyyy HHmm"};

    /** Date-only shapes we accept, e.g. 2019-10-15. */
    private static final String[] DATE_FORMATS = {"yyyy-MM-dd", "d-M-yyyy"};

    private static final DateTimeFormatter SAVE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter DATE_DISPLAY = DateTimeFormatter.ofPattern("MMM d yyyy");
    private static final DateTimeFormatter DATE_TIME_DISPLAY = DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a");

    private final LocalDateTime at;
    private final boolean hasTime;

    private TaskDate(LocalDateTime at, boolean hasTime) {
        this.at = at;
        this.hasTime = hasTime;
    }

    /** Reads a date written in any of the formats we accept. */
    public static TaskDate parse(String input) throws CrackException {
        // Slashes and dashes are the same to us, so we only match against dashes below.
        String cleaned = input.replace('/', '-');
        for (String format : DATE_TIME_FORMATS) {
            try {
                return new TaskDate(LocalDateTime.parse(cleaned, DateTimeFormatter.ofPattern(format)), true);
            } catch (DateTimeParseException e) {
                continue;
            }
        }
        for (String format : DATE_FORMATS) {
            try {
                LocalDate date = LocalDate.parse(cleaned, DateTimeFormatter.ofPattern(format));
                return new TaskDate(date.atStartOfDay(), false);
            } catch (DateTimeParseException e) {
                continue;
            }
        }
        throw new CrackException("'" + input + "' ain't a date I get. Try 2/12/2020 1500 or 2019-10-15.");
    }

    /** The day this lands on, ignoring any time. */
    public LocalDate toLocalDate() {
        return at.toLocalDate();
    }

    /** Just the day, written out for display. */
    public String dayDisplay() {
        return at.format(DATE_DISPLAY);
    }

    /** How this looks in the save file. */
    public String toSaveFormat() {
        return hasTime ? at.format(SAVE_FORMAT) : at.toLocalDate().toString();
    }

    @Override
    public String toString() {
        return at.format(hasTime ? DATE_TIME_DISPLAY : DATE_DISPLAY);
    }
}
