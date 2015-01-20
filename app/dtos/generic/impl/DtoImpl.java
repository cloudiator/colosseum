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

package dtos.generic.impl;

import dtos.generic.api.Dto;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by daniel on 18.12.14.
 */
public class DtoImpl implements Dto {

    private final Set<Link> links;

    public DtoImpl() {
        super();
        this.links = new HashSet<>();
    }

    @Override
    public Set<Link> getLinks() {
        return this.links;
    }
}
