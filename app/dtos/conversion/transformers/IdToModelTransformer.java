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

package dtos.conversion.transformers;

import javax.annotation.Nullable;

import models.generic.Model;
import models.service.ModelService;

/**
 * Created by daniel on 16.03.15.
 */
public class IdToModelTransformer<T extends Model> implements Transformer<Long, T> {

    private final ModelService<T> modelService;

    public IdToModelTransformer(ModelService<T> modelService) {
        this.modelService = modelService;
    }

    @Override public T transform(@Nullable Long id) {
        if (id == null) {
            return null;
        }
        return modelService.getById(id);
    }

    @Override public Long transformReverse(@Nullable T t) {
        if (t == null) {
            return null;
        }
        return t.getId();
    }
}
