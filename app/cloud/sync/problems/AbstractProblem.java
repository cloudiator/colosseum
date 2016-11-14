package cloud.sync.problems;

import cloud.sync.Problem;
import com.google.common.base.MoreObjects;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 13.11.16.
 */
public abstract class AbstractProblem<T> implements Problem<T> {

    private final T resource;

    public AbstractProblem(T resource) {
        checkNotNull(resource, "resource is null.");
        this.resource = resource;
    }

    @Override
    public T getResource() {
        return resource;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractProblem)) return false;

        AbstractProblem<?> that = (AbstractProblem<?>) o;

        return resource.equals(that.resource);

    }

    @Override
    public int hashCode() {
        return resource.hashCode();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("resource", resource).toString();
    }
}
