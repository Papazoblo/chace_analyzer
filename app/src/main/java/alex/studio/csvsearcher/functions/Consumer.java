package alex.studio.csvsearcher.functions;

@FunctionalInterface
public interface Consumer<T> {

    void accept(T obj);
}
