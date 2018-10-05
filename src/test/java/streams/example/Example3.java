package streams.example;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.is;

class Example3 {

    @Test
    void streamOfValues() {
        Stream<Integer> streamFromValues = Stream.of(1, 2, 3);

        Integer[] valuesInStream = streamFromValues.toArray(Integer[]::new);

        assertThat(valuesInStream, arrayContaining(1, 2, 3));
    }

    @Test
    void streamFromArrayUsingArraysStream() {
        Integer[] source = {1, 2, 3};

        Stream<Integer> stream = Arrays.stream(source);
        Integer[] valuesInStream = stream.toArray(Integer[]::new);

        assertThat(valuesInStream, arrayContaining(1, 2, 3));
    }

    @Test
    void streamLinesFromFile() throws IOException {
        File sourceFile = File.createTempFile("lines", "tmp");
        sourceFile.deleteOnExit();
        try (PrintWriter out = new PrintWriter(sourceFile)) {
            out.println("a");
            out.println("b");
            out.println("c");
        }

        Path pathToSourceFile = Paths.get(sourceFile.getAbsolutePath());
        try (Stream<String> stream = Files.lines(pathToSourceFile)) {
            String[] lines = stream.toArray(String[]::new);

            assertThat(lines, arrayContaining("a", "b", "c"));
        }
    }

    @Test
    void sequentialStreamFromCollection() {
        Collection<Integer> source = Arrays.asList(1, 2, 3);

        Stream<Integer> stream = source.stream();
        Integer[] valuesInStream = stream.toArray(Integer[]::new);

        assertThat(valuesInStream, arrayContaining(1, 2, 3));
    }

    @Test
    void parallelStreamFromCollection() {
        Collection<Integer> source = Arrays.asList(1, 2, 3);

        Stream<Integer> stream = source.parallelStream();
        Integer[] valuesInStream = stream.toArray(Integer[]::new);

        assertThat(valuesInStream, arrayContaining(1, 2, 3));
    }

    @Test
    void intStreamWithCodepointsFromString() {
        String source = "Hello";

        IntStream stream = source.chars();
        int[] valuesInStream = stream.toArray();

        assertThat(valuesInStream, is(new int[] {'H', 'e', 'l', 'l', 'o'}));
    }

    @Test
    void streamUsingBuilder() {
        Stream.Builder<String> builder = Stream.builder();

        Stream<String> stream = builder.add("Goodbye")
                                       .add("cruel")
                                       .add("world")
                                       .build();
        String[] valuesInStream = stream.toArray(String[]::new);

        assertThat(valuesInStream, arrayContaining("Goodbye", "cruel", "world"));
    }

    @Test
    void infinityStreamUsingGenerate() {
        Supplier<String> supplier = () -> "YOLO";

        Stream<String> stream = Stream.generate(supplier);

        String[] first3ValuesInStream = stream.limit(3)
                                              .toArray(String[]::new);

        assertThat(first3ValuesInStream, arrayContaining("YOLO", "YOLO", "YOLO"));
    }

    @Test
    void infinityStreamUsingIterate() {
        UnaryOperator<Integer> oddNumbersGenerator = n -> n + 2;

        Stream<Integer> stream = Stream.iterate(1, oddNumbersGenerator);
        Stream<Integer> limitedStream = stream.limit(10);
        Integer[] first10ValuesInStream = limitedStream.toArray(Integer[]::new);

        assertThat(first10ValuesInStream, arrayContaining(1, 3, 5, 7, 9, 11, 13, 15, 17, 19));
    }

    @Test
    void emptyStream() {
        Stream<String> stream = Stream.empty();

        assertThat(stream.count(), is(0));
    }

    @Test
    void streamFilesFromPath() throws IOException, URISyntaxException {
        Path resourcesPath = Paths.get(ClassLoader.getSystemResource("").toURI());

        Stream<Path> stream = Files.list(resourcesPath.resolve("top"));
        Path[] valuesInStream = stream.toArray(Path[]::new);

        assertThat(valuesInStream[0].getFileName().toString(), is("file1.txt"));
        assertThat(valuesInStream[1].getFileName().toString(), is("file2.txt"));
        assertThat(valuesInStream[2].getFileName().toString(), is("file3.bmp"));
    }

    @Test
    void streamFoundFilesFromPath() throws IOException, URISyntaxException {
        Path resourcesPath = Paths.get(ClassLoader.getSystemResource("").toURI());

        Stream<Path> stream = Files.find(resourcesPath, 10, (path, attributes) -> path.toFile().getName().endsWith(".bmp"));
        Path[] valuesInStream = stream.toArray(Path[]::new);

        assertThat(valuesInStream[0].getFileName().toString(), is("file3.bmp"));
    }

    @Test
    void streamFromRegex() {
        String source = "a:b:c";

        Stream<String> stream = Pattern.compile(":").splitAsStream(source);
        String[] valuesInStream = stream.toArray(String[]::new);

        assertThat(valuesInStream, arrayContaining("a", "b", "c"));
    }

    @Test
    void linesStreamFromReader() throws IOException {
        File sourceFile = File.createTempFile("lines", "tmp");
        sourceFile.deleteOnExit();
        try (PrintWriter out = new PrintWriter(sourceFile)) {
            out.println("a");
            out.println("b");
            out.println("c");
        }

        try (BufferedReader reader = Files.newBufferedReader(sourceFile.toPath())) {
            Stream<String> stream = reader.lines();
            String[] valuesInStream = stream.toArray(String[]::new);
            assertThat(valuesInStream, arrayContaining("a", "b", "c"));
        }
    }
}
