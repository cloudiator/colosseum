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

import models.generic.Model;

import javax.persistence.*;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 04.11.14.
 */
@Entity public class OperatingSystem extends Model {

    @Enumerated(EnumType.STRING) private OperatingSystemArchitecture operatingSystemArchitecture;
    @OneToMany(mappedBy = "operatingSystem") private List<Image> images;
    @ManyToOne private OperatingSystemVendor operatingSystemVendor;
    private String version;


    /**
     * Empty constructor for hibernate.
     */
    protected OperatingSystem() {
    }

    public OperatingSystem(OperatingSystemVendor operatingSystemVendor,
        OperatingSystemArchitecture operatingSystemArchitecture, String version) {
        checkNotNull(operatingSystemVendor);
        checkNotNull(operatingSystemArchitecture);
        checkNotNull(version);
        checkArgument(!version.isEmpty());
        this.operatingSystemVendor = operatingSystemVendor;
        this.operatingSystemArchitecture = operatingSystemArchitecture;
        this.version = version;
    }

    public OperatingSystemArchitecture getOperatingSystemArchitecture() {
        return operatingSystemArchitecture;
    }

    public List<Image> getImages() {
        return images;
    }

    public String getVersion() {
        return version;
    }

    public OperatingSystemVendor getOperatingSystemVendor() {
        return operatingSystemVendor;
    }
}
