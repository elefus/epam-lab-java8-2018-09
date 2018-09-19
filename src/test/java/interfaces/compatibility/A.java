package interfaces.compatibility;

@SuppressWarnings("all")
public interface A {

    default String superMethod() {
        return "Super method from A!";
    }
}
