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

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Superclass for all model classes.
 * <p>
 * Defines the auto generated id for
 * each model class.
 */
@MappedSuperclass
public abstract class Model extends Resource implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    /**
     * Empty constructor for hibernate.
     */
    protected Model() {
    }

    /**
     * Getter for the id.
     *
     * @return the identifies for this model object.
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter for the id.
     *
     * @param id the identified for this model object
     */
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("Model{id=%d}", id);
    }
}
