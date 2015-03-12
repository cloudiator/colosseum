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
import dtos.generic.impl.ValidatableDto;
import models.IpType;
import models.service.api.VirtualMachineService;
import play.data.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daniel on 12.03.15.
 */
public class IpAddressDto extends ValidatableDto {

    public static class References {
        @Inject
        public static Provider<VirtualMachineService> virtualMachineService;
    }

    public IpAddressDto() {
        super();
    }

    public IpAddressDto(String ip, String ipType, Long virtualMachine) {
        this.ip = ip;
        this.ipType = ipType;
        this.virtualMachine = virtualMachine;
    }

    private String ip;
    private String ipType;
    private Long virtualMachine;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIpType() {
        return ipType;
    }

    public void setIpType(String ipType) {
        this.ipType = ipType;
    }

    public Long getVirtualMachine() {
        return virtualMachine;
    }

    public void setVirtualMachine(Long virtualMachine) {
        this.virtualMachine = virtualMachine;
    }

    @Override
    public List<ValidationError> validateNotNull() {
        List<ValidationError> errors = new ArrayList<>();

        if (virtualMachine == null) {
            errors.add(new ValidationError("virtualMachine", "The virtual machine id is mandatory."));
        } else {
            if (References.virtualMachineService.get().getById(virtualMachine) == null) {
                errors.add(new ValidationError("virtualMachine", "The virtual machine id is illegal."));
            }
        }

        if (ip == null) {
            errors.add(new ValidationError("ip", "The ip is mandatory."));
        } else {
            if (!ValidatorHelper.isValidIpAddress(ip)) {
                errors.add(new ValidationError("ip", "The ip is not a valid ip address"));
            }
        }

        if (ipType == null) {
            errors.add(new ValidationError("ipType", "The ip type is mandatory."));
        } else {
            try {
                IpType.valueOf(ipType);
            } catch (IllegalArgumentException e) {
                errors.add(new ValidationError("ipType", "The ipType value is illegal."));
            }
        }

        return errors;
    }
}
