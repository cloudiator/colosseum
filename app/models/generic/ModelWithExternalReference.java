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

import com.google.common.collect.ImmutableMap;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MapKeyColumn;

/**
 * Created by Frank on 20.05.2015.
 */
@Entity @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class ModelWithExternalReference extends Model {

    @ElementCollection @MapKeyColumn(name = "refName") @Column(name = "refValue")
    private Map<String, String> externalReferences;

    public Map<String, String> externalReferences() {
        return ImmutableMap.copyOf(externalReferences);
    }

    public void addExternalReference(String tagName, String tagValue) {
        this.externalReferences.put(tagName, tagValue);
    }
}
