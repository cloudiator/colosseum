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

package api.binding.binders;

import api.binding.AbstractModelToDtoBinding;
import api.dto.ApiDto;
import models.Api;

/**
 * Created by daniel on 10.04.15.
 */
public class ApiModelToDtoBinding extends AbstractModelToDtoBinding<Api, ApiDto> {

    protected ApiModelToDtoBinding() {
        super(Api.class, ApiDto.class);
    }

    @Override public void configure() {
        binding().fromField("id").toField("id");
        binding().fromField("name").toField("name");
        binding().fromField("internalProviderName").toField("internalProviderName");
    }
}
