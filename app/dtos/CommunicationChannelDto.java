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

package dtos;

import dtos.generic.NeedsValidationDto;
import play.data.validation.ValidationError;

import java.util.List;

/**
 * Created by daniel on 09.01.15.
 */
public class CommunicationChannelDto extends NeedsValidationDto {

    protected Long communication;
    protected Long provider;
    protected Long consumer;

    public CommunicationChannelDto() {
        super();
    }

    @Override public void validation() {

    }

    public CommunicationChannelDto(Long communication, Long provider, Long consumer) {
        this.communication = communication;
        this.provider = provider;
        this.consumer = consumer;
    }

    public Long getCommunication() {
        return communication;
    }

    public void setCommunication(Long communication) {
        this.communication = communication;
    }

    public Long getProvider() {
        return provider;
    }

    public void setProvider(Long provider) {
        this.provider = provider;
    }

    public Long getConsumer() {
        return consumer;
    }

    public void setConsumer(Long consumer) {
        this.consumer = consumer;
    }
}
