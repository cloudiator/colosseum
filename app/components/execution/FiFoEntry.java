package components.execution;

import javax.annotation.Nonnull;
import java.util.concurrent.atomic.AtomicLong;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A FIFO Entry.
 * <p>
 * Wraps a comparable to offer a tie-breaker if the
 * {@link Comparable} returns 0 (equal).
 * <p>
 * Implementation is taken from the suggestions given in
 * {@link java.util.concurrent.PriorityBlockingQueue}
 *
 * @param <E> the object to wrap.
 */
class FiFoEntry<E extends Comparable<? super E>> implements Comparable<FiFoEntry<E>> {
    private static final AtomicLong seq = new AtomicLong(0);
    private final long seqNum;
    private final E entry;

    static <E extends Comparable<? super E>> FiFoEntry<E> of(E entry) {
        return new FiFoEntry<>(entry);
    }

    private FiFoEntry(E entry) {
        seqNum = seq.getAndIncrement();
        this.entry = entry;
    }

    public E getEntry() {
        return entry;
    }

    public int compareTo(@Nonnull FiFoEntry<E> other) {
        checkNotNull(other);
        int res = entry.compareTo(other.entry);
        if (res == 0 && other.entry != this.entry) {
            if (seqNum < other.seqNum)
                res = -1;
            else
                res = 1;
        }
        return res;
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        FiFoEntry<?> fiFoEntry = (FiFoEntry<?>) o;

        if (seqNum != fiFoEntry.seqNum)
            return false;
        //noinspection EqualsBetweenInconvertibleTypes
        return entry.equals(fiFoEntry.entry);

    }

    @Override public int hashCode() {
        int result = (int) (seqNum ^ (seqNum >>> 32));
        result = 31 * result + (entry != null ? entry.hashCode() : 0);
        return result;
    }
}
