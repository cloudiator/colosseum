/*
 * Copyright (c) 2014-2016 University of Ulm
 *
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.  Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package models.requirement;

import com.google.common.collect.Maps;
import models.RequirementValue;

import java.util.Map;
import java.util.NoSuchElementException;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 20.06.16.
 */
public class RequirementOperators {

    public static RequirementOperatorStorage requirementOperatorStorage =
        new RequirementOperatorStorage();

    static {
        requirementOperatorStorage
            .register(Long.class, Long.class, RequirementOperatorType.EQUAL, new LongEqualTo());
        requirementOperatorStorage
            .register(String.class, String.class, RequirementOperatorType.EQUAL,
                new StringEqualTo());
    }

    /**
     * @param typeLeft  the type of the left value.
     * @param typeRight the type of the right value.
     * @param type      the operation type.
     * @return the operator if it exists.
     * @throws NoSuchElementException if the operator can not be loaded.
     */
    public static RequirementOperator load(Class typeLeft, Class typeRight,
        RequirementOperatorType type) {
        return requirementOperatorStorage.retrieve(typeLeft, typeRight, type);
    }

    public static class LongEqualTo implements RequirementOperator {
        @Override public boolean evaluate(RequirementValue valueLeft, RequirementValue valueRight) {
            if (!(valueLeft.type().equals(Long.class) && valueRight.type().equals(Long.class))) {
                throw new IllegalArgumentException();
            }
            return ((Long) valueLeft.value()).compareTo((Long) valueRight.value()) == 0;
        }
    }


    public static class StringEqualTo implements RequirementOperator {
        @Override public boolean evaluate(RequirementValue valueLeft, RequirementValue valueRight) {
            if (!(valueLeft.type().equals(String.class) && valueRight.type().equals(String.class)))
                throw new IllegalArgumentException();
            return valueLeft.value().equals(valueRight.value());
        }
    }


    private static class RequirementOperatorStorage {

        private final Map<StorageIdentifier, RequirementOperator> storage;

        private RequirementOperatorStorage() {
            this.storage = Maps.newHashMap();
        }

        public void register(Class typeLeft, Class typeRight, RequirementOperatorType type,
            RequirementOperator requirementOperator) {
            this.storage.put(new StorageIdentifier(typeLeft, typeRight, type), requirementOperator);
        }

        public void unregister(Class typeLeft, Class typeRight, RequirementOperatorType type) {
            this.storage.remove(new StorageIdentifier(typeLeft, typeRight, type));
        }

        public RequirementOperator retrieve(Class typeLeft, Class typeRight,
            RequirementOperatorType type) {

            final RequirementOperator requirementOperator =
                storage.get(new StorageIdentifier(typeLeft, typeRight, type));
            if (requirementOperator == null) {
                throw new NoSuchElementException(String.format(
                    "Could not retrieve RequirementOperator for typeLeft: %s, typeRight: %s, RequirementOperatorType: %s",
                    typeLeft, typeRight, type));
            }
            return requirementOperator;
        }

        private static class StorageIdentifier {
            private final Class typeLeft;
            private final Class typeRight;
            private final RequirementOperatorType type;

            private StorageIdentifier(Class typeLeft, Class typeRight,
                RequirementOperatorType type) {
                checkNotNull(typeLeft);
                checkNotNull(typeRight);
                checkNotNull(type);
                this.typeLeft = typeLeft;
                this.typeRight = typeRight;
                this.type = type;
            }

            @Override public boolean equals(Object o) {
                if (this == o)
                    return true;
                if (!(o instanceof StorageIdentifier))
                    return false;

                StorageIdentifier that = (StorageIdentifier) o;

                return typeLeft.equals(that.typeLeft) && typeRight.equals(that.typeRight)
                    && type == that.type;

            }

            @Override public int hashCode() {
                int result = typeLeft.hashCode();
                result = 31 * result + typeRight.hashCode();
                result = 31 * result + type.hashCode();
                return result;
            }

            public Class typeLeft() {
                return typeLeft;
            }

            public Class typeRight() {
                return typeRight;
            }

            public RequirementOperatorType type() {
                return type;
            }
        }
    }

}
