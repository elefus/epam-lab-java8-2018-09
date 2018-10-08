package streams.part2.example.data;

import lambda.data.Person;

public class PersonPositionDuration {

    private final Person person;
    private final String position;
    private final int duration;

    public PersonPositionDuration(Person person, String position, int duration) {
        this.person = person;
        this.position = position;
        this.duration = duration;
    }

    public Person getPerson() {
        return person;
    }

    public String getPosition() {
        return position;
    }

    public int getDuration() {
        return duration;
    }
}