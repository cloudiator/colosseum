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

package dtos.conversion.converters;

import dtos.conversion.AbstractConverter;
import dtos.generic.RemoteDto;
import de.uniulm.omi.cloudiator.persistance.entities.RemoteResource;

/**
 * Created by daniel on 10.08.15.
 */
public abstract class RemoteConverter<T extends RemoteResource, S extends RemoteDto>
    extends AbstractConverter<T, S> {

    protected RemoteConverter(Class<T> t, Class<S> s) {
        super(t, s);
    }

    @Override public void configure() {
        binding().fromField("remoteId").toField("remoteId");
        binding().fromField("providerId").toField("providerId");
        binding().fromField("swordId").toField("swordId");
        binding().fromField("remoteState").toField("remoteState");
    }
}
