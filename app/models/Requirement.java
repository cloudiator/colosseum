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

package models;

import models.generic.Model;
import models.requirement.RequirementField;
import models.requirement.RequirementOperatorType;
import models.requirement.RequirementOperators;

import javax.persistence.Entity;

/**
 * Created by daniel on 20.06.16.
 */
@Entity public class Requirement extends Model {

    RequirementField field;
    RequirementOperatorType operatorType;
    RequirementValue value;

    protected Requirement() {

    }

    public Requirement(RequirementField field, RequirementOperatorType operatorType,
        RequirementValue value) {
        this.field = field;
        this.operatorType = operatorType;
        this.value = value;
    }

    public boolean evaluate(Object o) {
        return RequirementOperators.load(field.type(), value.type(), operatorType)
            .evaluate(field.getValue(o), value);
    }

    public Class type() {
        return field.type();
    }
}
