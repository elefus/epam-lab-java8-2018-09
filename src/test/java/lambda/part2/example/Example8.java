package lambda.part2.example;

import java.util.List;

@SuppressWarnings("unused")
public class Example8 {

    private interface BusinessObject {

        BusinessObject createModel(String value);

        BusinessObject transform(BusinessObject object);

        List<String> validate(BusinessObject object);

        default List<String> process(String value) {
            BusinessObject model = createModel(value);
            BusinessObject transformed = transform(model);
            return validate(transformed);
        }
    }
}
