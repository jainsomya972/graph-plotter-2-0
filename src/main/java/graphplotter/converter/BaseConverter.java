package graphplotter.converter;

import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseConverter<U, V> {
    public abstract V convert(U u);
    public abstract U backwards(V v);

    public List<V> convertAll(List<U> uList) {
        return uList.stream()
                    .map(this::convert)
                    .collect(Collectors.toList());
    }

    public List<U> backwardsAll(List<V> uList) {
        return uList.stream()
                .map(this::backwards)
                .collect(Collectors.toList());
    }
}
