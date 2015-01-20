/*
 * Copyright (c) 2014-2015 University of Ulm
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

package models.generic;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Superclass for models having a name property.
 * <p>
 * Provides setters and getters for the name property.
 */
@MappedSuperclass
public abstract class NamedModel extends Model {

    /**
     * Default constructor for hibernate.
     */
    public NamedModel() {
    }

    /**
     * Constructor setting the name property.
     *
     * @param name the name.
     */
    public NamedModel(String name) {
        this.name = name;
    }

    @Column(unique = true, nullable = false)
    protected String name;

    @Override
    public String toString() {
        return name;
    }

    /**
     * Getter for the name.
     *
     * @return the value of the name property.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the name.
     * <p>
     * The name must be unique for all stored
     * entities of this type.
     *
     * @param name the value of the name property.
     */
    public void setName(String name) {
        this.name = name;
    }
}
