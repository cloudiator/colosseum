package components;

import java.io.File;
import java.util.Optional;
import java.util.Set;

/**
 * Created by daniel on 06.06.16.
 */
public class CompositeLogCollector implements LogCollector {

    private final Set<LogCollector> collectors;

    public CompositeLogCollector(Set<LogCollector> collectors) {
        this.collectors = collectors;
    }

    @Override public Optional<File> get() {
        //write zip file?
        return Optional.empty();
    }
}
