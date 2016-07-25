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


import de.uniulm.omi.cloudiator.common.os.OperatingSystemArchitecture;
import de.uniulm.omi.cloudiator.common.os.OperatingSystemFamily;
import de.uniulm.omi.cloudiator.common.os.OperatingSystemVersion;
import models.generic.Model;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 04.11.14.
 */
@Entity public class OperatingSystem extends Model
    implements de.uniulm.omi.cloudiator.common.os.OperatingSystem {

    @Column(nullable = false) @Enumerated(EnumType.STRING) private OperatingSystemArchitecture
        operatingSystemArchitecture;

    @Column(nullable = false) @Enumerated(EnumType.STRING) private OperatingSystemFamily operatingSystemFamily;

    @Nullable private String version;

    @OneToMany(mappedBy = "operatingSystem") private List<Image> images;


    /**
     * Empty constructor for hibernate.
     */
    protected OperatingSystem() {
    }

    public OperatingSystem(OperatingSystemArchitecture operatingSystemArchitecture,
        OperatingSystemFamily operatingSystemFamily, @Nullable String version) {

        checkNotNull(operatingSystemArchitecture);
        checkNotNull(operatingSystemFamily);

        this.operatingSystemArchitecture = operatingSystemArchitecture;
        this.operatingSystemFamily = operatingSystemFamily;
        this.version = version;
    }

    public OperatingSystem(de.uniulm.omi.cloudiator.common.os.OperatingSystem operatingSystem) {
        checkNotNull(operatingSystem);
        this.operatingSystemArchitecture = operatingSystem.operatingSystemArchitecture();
        this.operatingSystemFamily = operatingSystem.operatingSystemFamily();
        this.version = operatingSystem.operatingSystemVersion().toString();
    }

    @Override public OperatingSystemFamily operatingSystemFamily() {
        return operatingSystemFamily;
    }

    @Override public OperatingSystemArchitecture operatingSystemArchitecture() {
        return operatingSystemArchitecture;
    }

    @Override public OperatingSystemVersion operatingSystemVersion() {
        return OperatingSystemVersion
            .of(version, operatingSystemFamily.operatingSystemVersionFormat());
    }
}
