/*
 * Copyright (c) 2015 University of Ulm
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

package dtos.convert.converters.api;

import dtos.generic.api.Dto;
import dtos.generic.impl.Link;
import models.generic.Model;

import java.util.Set;

/**
 * @param <T>
 * @param <S>
 */
public interface ModelDtoConverter<T extends Model, S extends Dto> {

    public T toModel(S dto);

    public T toModel(S dto, T model);

    public S toDto(T model);

    public S toDto(T model, Set<Link> links);

}
