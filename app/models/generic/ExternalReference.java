/*
 * Copyright (c) 2014-2015 University of Ulm
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

package models.generic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Created by Frank on 20.05.2015.
 */
@Entity
public class ExternalReference extends Model {

    @Column(nullable = false, updatable = false) private String reference;
    //@ManyToOne private ModelWithExternalReference modelWithExternalReference;

    /**
     * no-args constructor for hibernate.
     */
    protected ExternalReference() {
    }

    public ExternalReference(String reference) {
        this.reference = reference;
    }

    public String getReference() {
        return reference;
    }
}
