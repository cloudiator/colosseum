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

import models.Image;
import models.RequirementValue;

/**
 * Created by daniel on 20.06.16.
 */
public enum RequirementField {
    IMAGE_ID {
        @Override public RequirementValue getValue(Object entity) {
            if (!(entity instanceof Image)) {
                throw new IllegalArgumentException();
            }
            return new RequirementValue(String.class, ((Image) entity).getId());
        }

        @Override public Class type() {
            return Image.class;
        }
    };

    public abstract RequirementValue getValue(Object entity);

    public abstract Class type();
}
