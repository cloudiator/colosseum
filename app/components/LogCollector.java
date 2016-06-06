package components;

import java.io.File;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Created by daniel on 06.06.16.
 */
public interface LogCollector extends Supplier<Optional<File>> {
    @Override Optional<File> get();
}
