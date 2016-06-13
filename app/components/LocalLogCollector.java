package components;

import com.google.common.base.MoreObjects;

import java.io.File;
import java.util.Optional;

/**
 * Created by daniel on 06.06.16.
 */
public class LocalLogCollector implements LogCollector {

    private final File file;

    public LocalLogCollector(File file) {
        this.file = file;
    }

    public LocalLogCollector(String path) {
        this(new File(path));
    }

    @Override public Optional<File> get() {
        return Optional.of(file);
    }

    @Override public String toString() {
        return MoreObjects.toStringHelper(this).add("file",file).toString();
    }
}
