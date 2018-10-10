package streams.part2.example;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class Example5 {

    @Test
    void wordCountingSequentially() {
        long count = 0;
        double average = 0;

        assertThat(count, is(59L));
        assertThat(average, Matchers.closeTo(5.22, 0.001));
    }

    @Test
    void wordCountingParallel() throws Exception {
        long count = 0;
        double average = 0;

        assertThat(count, is(59L));
        assertThat(average, Matchers.closeTo(5.22, 0.001));
    }

    @Test
    void wordCountingConcurrently() throws Exception {
        long count = 0;
        double average = 0;

        assertThat(count, is(59L));
        assertThat(average, Matchers.closeTo(5.22, 0.001));
    }

    private static String[] getData() {
        return new String[]{
                "Будет ласковый дождь, будет запах земли,",
                "Щебет юрких стрижей от зари до зари,",
                "И ночные рулады лягушек в прудах,",
                "И цветение слив в белопенных садах.",
                "Огнегрудый комочек слетит на забор,",
                "И малиновки трель выткет звонкий узор.",
                "И никто, и никто не вспомянет войну —",
                "Пережито-забыто, ворошить ни к чему.",
                "И ни птица, ни ива слезы не прольёт,",
                "Если сгинет с Земли человеческий род.",
                "И весна… и весна встретит новый рассвет,",
                "Не заметив, что нас уже нет."
        };
    }
}
