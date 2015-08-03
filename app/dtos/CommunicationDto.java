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

import com.google.inject.Inject;
import com.google.inject.Provider;
import dtos.generic.ValidatableDto;
import dtos.validation.validators.ModelIdValidator;
import dtos.validation.validators.NotNullValidator;
import models.PortRequired;
import models.PortProvided;
import models.service.ModelService;

/**
 * Created by daniel on 09.01.15.
 */
public class CommunicationDto extends ValidatableDto {

    protected Long inboundPort;
    protected Long outboundPort;

    public CommunicationDto() {
        super();
    }

    @Override public void validation() {
        validator(Long.class).validate(inboundPort).withValidator(new NotNullValidator())
            .withValidator(
                new ModelIdValidator<>(References.inboundPortModelServiceProvider.get()));
        validator(Long.class).validate(outboundPort).withValidator(new NotNullValidator())
            .withValidator(
                new ModelIdValidator<>(References.outboundPortModelServiceProvider.get()));

    }

    public Long getInboundPort() {
        return inboundPort;
    }

    public void setInboundPort(Long inboundPort) {
        this.inboundPort = inboundPort;
    }

    public Long getOutboundPort() {
        return outboundPort;
    }

    public void setOutboundPort(Long outboundPort) {
        this.outboundPort = outboundPort;
    }

    public static class References {

        @Inject private static Provider<ModelService<PortRequired>> inboundPortModelServiceProvider;
        @Inject private static Provider<ModelService<PortProvided>>
            outboundPortModelServiceProvider;

        private References() {
        }
    }
}
