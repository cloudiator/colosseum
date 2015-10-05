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

package models;

import com.google.common.collect.ImmutableList;
import models.generic.RemoteResourceInLocation;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Entity public class Hardware extends RemoteResourceInLocation {

    @Column(updatable = false, nullable = false) private String name;

    @ManyToOne(optional = false) private HardwareOffer hardwareOffer;

    @OneToMany(mappedBy = "hardware", cascade = CascadeType.REMOVE)
    private List<VirtualMachineTemplate> virtualMachineTemplates;

    /**
     * Empty constructor for hibernate.
     */
    protected Hardware() {
    }

    public Hardware(String remoteId, Cloud cloud, Location location, String name,
        HardwareOffer hardwareOffer) {
        super(remoteId, cloud, location);

        checkNotNull(name);
        checkArgument(!name.isEmpty());

        this.name = name;
        this.hardwareOffer = hardwareOffer;
    }

    public HardwareOffer hardwareOffer() {
        return hardwareOffer;
    }

    public List<VirtualMachineTemplate> virtualMachineTemplatesUsedFor() {
        return ImmutableList.copyOf(virtualMachineTemplates);
    }

    public String name() {
        return name;
    }

}
