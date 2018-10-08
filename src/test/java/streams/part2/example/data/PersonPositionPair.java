package streams.part2.example.data;

import lambda.data.Person;

public class PersonPositionPair {

    private final Person person;
    private final String position;

    public PersonPositionPair(Person person, String position) {
        this.person = person;
        this.position = position;
    }

    public Person getPerson() {
        return person;
    }

    public String getPosition() {
        return position;
    }
}
