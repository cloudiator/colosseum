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

package dtos;

import dtos.generic.impl.ValidatableDto;
import play.data.validation.ValidationError;

import java.util.List;

/**
 * Created by daniel on 09.01.15.
 */
public class CommunicationDto extends ValidatableDto {

    public Long provider;
    public Long consumer;

    public CommunicationDto(Long provider, Long consumer) {
        this.provider = provider;
        this.consumer = consumer;
    }

    public CommunicationDto() {
    }

    @Override
    public List<ValidationError> validateNotNull() {
        return super.validateNotNull();
    }
}
