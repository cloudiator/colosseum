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
import dtos.validation.IpAddressValidator;
import dtos.validation.ModelIdValidator;
import dtos.validation.NotNullOrEmptyValidator;
import dtos.validation.NotNullValidator;
import models.IpType;
import models.VirtualMachine;
import models.service.impl.generic.BaseModelService;

/**
 * Created by daniel on 12.03.15.
 */
public class IpAddressDto extends ValidatableDto {

    private String ip;
    private IpType ipType;
    private Long virtualMachine;

    public IpAddressDto() {
        super();
    }

    @Override public void validation() {
        validator(String.class).validate(ip).withValidator(new NotNullOrEmptyValidator())
            .withValidator(new IpAddressValidator());
        validator(IpType.class).validate(ipType).withValidator(new NotNullValidator());
        validator(Long.class).validate(virtualMachine).withValidator(new NotNullValidator())
            .withValidator(new ModelIdValidator<>(References.virtualMachineService.get()));
    }

    public IpAddressDto(String ip, IpType ipType, Long virtualMachine) {
        this.ip = ip;
        this.ipType = ipType;
        this.virtualMachine = virtualMachine;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public IpType getIpType() {
        return ipType;
    }

    public void setIpType(IpType ipType) {
        this.ipType = ipType;
    }

    public Long getVirtualMachine() {
        return virtualMachine;
    }

    public void setVirtualMachine(Long virtualMachine) {
        this.virtualMachine = virtualMachine;
    }

    public static class References {

        private References() {
        }

        @Inject private static Provider<BaseModelService<VirtualMachine>> virtualMachineService;
    }
}
