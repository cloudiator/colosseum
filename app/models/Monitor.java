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

import components.scalability.internal.TsdbLocator;
import models.generic.ModelWithExternalReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Frank on 20.05.2015.
 */
@Entity
public abstract class Monitor extends ModelWithExternalReference {

    @OneToMany(mappedBy = "monitor") private List<MonitorInstance>
        monitorInstances = new ArrayList<>();;

    public List<MonitorInstance> getMonitorInstances() {
        return monitorInstances;
    }

    public void setMonitorInstances(List<MonitorInstance> monitorInstances) {
        this.monitorInstances = monitorInstances;
    }

//    @ManyToMany(mappedBy = "monitors") private List<ComposedMonitor>
//        composedMonitors;
//
//    public List<ComposedMonitor> getComposedMonitors() {
//        return composedMonitors;
//    }
//
//    public void setComposedMonitors(List<ComposedMonitor> composedMonitors) {
//        this.composedMonitors = composedMonitors;
//    }



    /**
     * Method to get TSDB Locator - composition pattern.
     * @return Tbe actual TSDB Locator for this class.
     */
    protected abstract TsdbLocator getTsdbLocator();

    /**
     * Gets the TSDB IPs for a Monitor in respect to its MonitorInstances.
     * @return The map of IP and all MonitorInstances to this IP.
     */
    public Map<String, List<MonitorInstance>> getTsdbIp(){
        return getTsdbLocator().getTsdbIp();
    }
}
