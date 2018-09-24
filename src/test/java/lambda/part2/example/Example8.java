package lambda.part2.example;

import java.util.List;
import java.util.function.Function;

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

        default List<String> inverseChaining(String value) {
            return validate(transform(createModel(value)));
        }

        default List<String> chaining(String value) {
            return first(this::createModel)
                .andThen(this::transform)
                .andThen(this::validate)
                .apply(value);
        }
    }

    private static <T, R> Function<T, R> first(Function<T, R> function) {
        return function;
    }
}
