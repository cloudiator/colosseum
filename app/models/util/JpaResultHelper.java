/*
 * Copyright (c) 2015 University of Ulm
 *
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.  Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

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
