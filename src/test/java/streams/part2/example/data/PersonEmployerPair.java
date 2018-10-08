package streams.part2.example.data;

import lambda.data.Person;

public class PersonEmployerPair {

    private final String employer;
    private final Person person;

    public PersonEmployerPair(String employer, Person person) {
        this.employer = employer;
        this.person = person;
    }

    public String getEmployer() {
        return employer;
    }

    public Person getPerson() {
        return person;
    }
}
