import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FunctionalList<T> {
    private final List<T> internalList;

    private FunctionalList(List<T> list) {
        this.internalList = list;
    }

    public static <T> FunctionalList<T> of(List<T> list) {
        return new FunctionalList<>(list);
    }

    public <R> FunctionalList<R> map(Function<T, R> mapper) {
        return new FunctionalList<>(internalList.stream().map(mapper).collect(Collectors.toList()));
    }

    public <R> FunctionalList<R> flatMap(Function<T, Stream<R>> mapper) {
        return new FunctionalList<>(internalList.stream().flatMap(mapper).collect(Collectors.toList()));
    }

    public List<T> toList() {
        return internalList;
    }
}
