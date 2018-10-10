package streams.part2.example.data;

import lambda.data.Person;
import lombok.Value;

@Value
public class PersonDurationPair {

    Person person;
    int duration;
}
