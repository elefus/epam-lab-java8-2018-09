package lambda.data;

import lombok.Value;

@Value
public class PersonEmployer {

    Person person;
    String employer;
}
