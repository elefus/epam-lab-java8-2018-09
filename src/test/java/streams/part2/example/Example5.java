package streams.part2.example;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

class Example5 {

    /**
     * Выбирает из текста наиболее часто встречающиеся слова.
     * Подсчет слов выполняется без учета их регистра, т.е. "Привет", "привет", "пРиВеТ" - одно и то же слово.
     * Если некоторые слова имеют одинаковую частоту, то в выходном списке они упорядочиваются в лексикографическом порядке.
     * @param text Исходный текст в котором слова (в смешанном регистре) разделены пробелами.
     * @param numberWords Количество наиболее часто встречающихся слов, которые необходимо отобрать.
     * @return Список отобранных слов (в нижнем регистре).
     */
    private List<String> getFrequentlyOccurringWords(String text, int numberWords) {
        return Pattern.compile("\\s+")
                      .splitAsStream(text)
                      .map(string -> string.replaceAll("[^a-zA-Zа-яА-ЯёЁ]", ""))
                      .filter(((Predicate<String>) String::isEmpty).negate())
                      .map(String::toLowerCase)
                      .parallel()
                      .collect(groupingBy(identity(), counting()))
                      .entrySet()
                      .stream()
                      .parallel()
                      .sorted(Comparator.<Entry<String, Long>>comparingLong(Entry::getValue).reversed().thenComparing(Entry::getKey))
                      .limit(numberWords)
                      .map(Entry::getKey)
                      .collect(Collectors.toList());
    }

    @Test
    void test1() {
        String source = "Мама мыла мыла мыла раму";

        List<String> result = getFrequentlyOccurringWords(source, 5);



        assertThat(result, contains("мыла", "мама", "раму"));
    }

    @Test
    void test2() {
        String source = "Lorem  ipsum dolor sit,    amet consectetur adipiscing elit Sed sodales consectetur purus at "
                + "faucibus Donec mi quam tempor vel ipsum non faucibus suscipit massa Morbi lacinia velit "
                + "blandit tincidunt efficitur Vestibulum. eget metus imperdiet sapien laoreet faucibus Nunc "
                + "eget vehicula mauris ac auctor lorem Lorem ipsum dolor sit amet consectetur adipiscing elit "
                + "Integer vel odio nec mi tempor dignissim";

        List<String> result = getFrequentlyOccurringWords(source, 10);

        assertThat(result, contains("consectetur", "faucibus", "ipsum", "lorem", "adipiscing", "amet", "dolor", "eget", "elit", "mi"));
    }
}
