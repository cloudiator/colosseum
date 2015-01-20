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

package models.service.impl.generic;

import models.repository.api.generic.NamedModelRepository;
import models.service.api.generic.NamedModelServiceInterface;
import models.generic.NamedModel;

/**
 * Created by daniel on 31.10.14.
 */
public abstract class NamedModelService<T extends NamedModel> extends ModelService<T> implements NamedModelServiceInterface<T> {

    protected NamedModelRepository<T> modelRepository;

    public NamedModelService(NamedModelRepository<T> namedModelRepository) {
        super(namedModelRepository);
    }

    @Override
    public T getByName(String name) {
        return modelRepository.findByName(name);
    }
}
