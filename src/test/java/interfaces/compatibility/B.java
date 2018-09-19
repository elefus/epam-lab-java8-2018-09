package interfaces.compatibility;

@SuppressWarnings("all")
public interface B {

    default String superMethod() {
        return "From B interface";
    }
}
