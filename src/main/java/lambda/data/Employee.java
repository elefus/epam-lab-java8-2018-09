package lambda.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Employee {

    private final Person person;
    private final List<JobHistoryEntry> jobHistory;

    public Employee(Person person, List<JobHistoryEntry> jobHistory) {
        this.person = person;
        this.jobHistory = new ArrayList<>(jobHistory);
    }

    public Person getPerson() {
        return person;
    }

    public List<JobHistoryEntry> getJobHistory() {
        return new ArrayList<>(jobHistory);
    }

    @Override
    public String toString() {
        return "Employee@" + hashCode() + " {"
             + "person=" + person + ", "
             + "jobHistory=" + jobHistory + "}";
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Employee employee = (Employee) object;
        return Objects.equals(person, employee.person)
            && Objects.equals(jobHistory, employee.jobHistory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(person, jobHistory);
    }
}
