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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;


@Entity public class HardwareOffer extends Model {

    @Column(nullable = false, updatable = false) private int numberOfCpu;

    @Column(nullable = false, updatable = false) private long mbOfRam;

    @Column(nullable = false, updatable = false) private long localDiskSpace;

    @OneToMany(mappedBy = "hardwareOffer", cascade = CascadeType.REMOVE) private List<Hardware> hardware;

    private HardwareOffer() {
    }

    private HardwareOffer(int numberOfCpu, long mbOfRam, long localDiskSpace) {
        this.numberOfCpu = numberOfCpu;
        this.mbOfRam = mbOfRam;
        this.localDiskSpace = localDiskSpace;
    }

    public int getNumberOfCpu() {
        return numberOfCpu;
    }

    public long getMbOfRam() {
        return mbOfRam;
    }

    public long getLocalDiskSpace() {
        return localDiskSpace;
    }

    public List<Hardware> getHardware() {
        return hardware;
    }
}
