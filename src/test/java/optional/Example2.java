package optional;

import org.junit.jupiter.api.Test;

import java.util.Optional;

public class Example2 {

    @Test
    void name() {

        Optional.empty().get();
    }
}
