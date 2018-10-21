package lambda.data;

import java.util.Objects;

public class JobHistoryEntry {

    private final String employer;
    private final String position;
    private final int duration;

    public JobHistoryEntry(int duration, String position, String employer) {
        this.duration = duration;
        this.position = position;
        this.employer = employer;
    }

    public int getDuration() {
        return duration;
    }

    public String getPosition() {
        return position;
    }

    public String getEmployer() {
        return employer;
    }

    @Override
    public String toString() {
        return "JobHistoryEntry@" + hashCode() + ": {"
             + "duration=" + duration + ", "
             + "position='" + position + "\', "
             + "employer='" + employer + "\'}";
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        JobHistoryEntry entry = (JobHistoryEntry) other;
        return duration == entry.duration
            && Objects.equals(position, entry.position)
            && Objects.equals(employer, entry.employer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(duration, position, employer);
    }
}
