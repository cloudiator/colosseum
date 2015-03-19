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

package models.repository.api.generic;

import models.generic.Model;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by daniel on 31.10.14.
 */
public interface ModelRepository<T extends Model> {

    @Nullable public T findById(Long id);

    //T create(T t);

    //public void flush();

    //T refresh(T t);

    public void delete(T t);

    //T update(T t);

    public void save(T t);

    public List<T> findAll();


}
