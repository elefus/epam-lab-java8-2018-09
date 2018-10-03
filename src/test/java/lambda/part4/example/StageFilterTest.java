package lambda.part4.example;

import lombok.Value;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StageFilterTest {

    @Nested
    class OnEmptySource {

        @Test
        void hasNextShouldReturnFalse() {
            Iterator<Object> source = Collections.emptyIterator();

            Iterator<Object> filter = new StageFilter<>(source, Objects::nonNull).iterator();

            assertThat(filter.hasNext(), is(false));
        }

        @Test
        void nextShouldThrowNoSuchElementException() {
            Iterator<Object> source = Collections.emptyIterator();

            Iterator<Object> filter = new StageFilter<>(source, Objects::nonNull).iterator();

            assertThrows(NoSuchElementException.class, filter::next);
        }
    }

    @Nested
    class OnNotEmptySource {

        @Test
        void hasNextShouldReturnTrueIfOneMatchesElementPresent() {
            Iterator<Integer> source = Arrays.asList(1, 2, 3, 4).iterator();

            Iterator<Integer> filter = new StageFilter<>(source, val -> val == 4).iterator();

            assertThat(filter.hasNext(), is(true));
        }

        @Test
        void nextShouldReturnFirstMatchesElementIfPresent() {
            @Value
            class Holder {
                int id;
            }

            Holder value = new Holder(0);
            Iterator<Holder> source = Arrays.asList(new Holder(1), new Holder(0), value).iterator();

            Iterator<Holder> filter = new StageFilter<>(source, value::equals).iterator();

            assertThat(filter.hasNext(), is(true));
            assertThat(filter.hasNext(), is(true));
            assertThat(filter.hasNext(), is(true));

            Holder firstFound = filter.next();
            assertThat(firstFound, is(value));
            assertThat(firstFound, not(sameInstance(value)));

            assertThat(filter.next(), sameInstance(value));
        }

        @Test
        void nextShouldCorrectlyMoveToNextMatchesElementOnEveryCall() {
            Iterator<Integer> source = Arrays.asList(1, 2, 3, 4).iterator();

            Iterator<Integer> filter = new StageFilter<>(source, val -> val > 2).iterator();

            assertThat(filter.next(), is(3));
            assertThat(filter.next(), is(4));
            assertThrows(NoSuchElementException.class, filter::next);
        }
    }

    @Nested
    class sourceContainsNullValues {

        @Test
        void hasNextShouldReturnTrue() {
            Iterator<Object> source = Collections.singleton(null).iterator();

            Iterator<Object> filter = new StageFilter<>(source, Objects::isNull).iterator();

            assertThat(filter.hasNext(), is(true));
        }

        @Test
        void nextShouldReturnNullValue() {
            Iterator<Object> source = Collections.singleton(null).iterator();

            Iterator<Object> filter = new StageFilter<>(source, Objects::isNull).iterator();

            assertThat(filter.next(), nullValue());
        }

        @Test
        void nextShouldCorrectlyMoveToNextMatchesElementOnEveryCall() {
            Iterator<Object> source = Arrays.<Object>asList(null, 10, null).iterator();

            Iterator<Object> filter = new StageFilter<>(source, Objects::isNull).iterator();

            assertThat(filter.next(), nullValue());
            assertThat(filter.next(), nullValue());
            assertThrows(NoSuchElementException.class, filter::next);
            assertThrows(NoSuchElementException.class, filter::next);
        }
    }
}
