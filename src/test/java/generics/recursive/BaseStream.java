package generics.recursive;

public interface BaseStream<T, S extends BaseStream<T, S>> {

    S sequential();
    S parallel();
}
