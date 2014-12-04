package models.util;

import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import java.util.List;

/**
 * Utility class for JPA Results.
 *
 * @author Daniel Baur.
 */
public class JpaResultHelper {

    /**
     * Private constructor. This is a static
     * utility class.
     */
    private JpaResultHelper() {
        throw new AssertionError();
    }

    /**
     * Executes the query and ensures that either a single
     * result is returned or null.
     *
     * @param query the query to be executed.
     * @return a single result of any, otherwise null.
     * @throws javax.persistence.NonUniqueResultException if more than one result is returned by the query.
     */
    public static Object getSingleResultOrNull(Query query) {
        List results = query.getResultList();
        if (results.isEmpty()) {
            return null;
        } else if (results.size() == 1) {
            return results.get(0);
        }
        throw new NonUniqueResultException();
    }

}
