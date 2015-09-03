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

package models;

import components.scalability.internal.EmptyTsdbLocator;
import components.scalability.internal.TsdbLocator;

import javax.persistence.Column;
import javax.persistence.Entity;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Frank on 20.05.2015.
 */
@Entity public class ConstantMonitor extends Monitor {


    @Column(nullable = false, updatable = false) private Double value;

    /**
     * Empty constructor for hibernate.
     */
    protected ConstantMonitor() {
    }

    public ConstantMonitor(Double value) {
        checkNotNull(value);
        this.value = value;
    }

    public Double getValue() {
        return value;
    }

    @Override protected TsdbLocator getTsdbLocator() {
        return new EmptyTsdbLocator();
    }
}
