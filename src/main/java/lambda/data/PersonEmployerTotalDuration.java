package lambda.data;

import lombok.Value;

@Value
public class PersonEmployerTotalDuration {

    Person person;
    String employer;
    int totalDuration;
}
