/*
 * Copyright (c) 2014-2015 University of Ulm
 *
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.  Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package components.job;

import cloud.colosseum.ColosseumComputeService;
import com.google.inject.Inject;
import models.Tenant;
import models.VirtualMachine;
import models.MonitorInstance;
import models.RawMonitor;
import models.ComposedMonitor;
import models.generic.RemoteState;
import models.service.ModelService;
import models.service.RemoteModelService;
import play.Logger;
import play.db.jpa.JPAApi;
import util.logging.Loggers;

/**
 * Created by daniel on 14.10.15.
 */
public class DeleteVirtualMachineJob extends AbstractRemoteResourceJob<VirtualMachine> {

    private Logger.ALogger LOGGER = Loggers.of(Loggers.CLOUD_JOB);

    private final RemoteModelService<VirtualMachine> virtualMachineRemoteModelService;
    private final ModelService<MonitorInstance> monitorInstanceModelService;
    private final ModelService<RawMonitor> rawMonitorModelService;
    //private final ModelService<ComposedMonitor> composedMonitorModelService;
    //TODO add ComposedMonitor service, once the aggregator are concerned by
    //TODO cross-VM distribution

    public DeleteVirtualMachineJob(JPAApi jpaApi, VirtualMachine virtualMachine,
        RemoteModelService<VirtualMachine> modelService, ModelService<Tenant> tenantModelService,
        ColosseumComputeService colosseumComputeService, Tenant tenant,
        ModelService<MonitorInstance> monitorInstanceModelService,
        ModelService<RawMonitor> rawMonitorModelService) {
        super(jpaApi, virtualMachine, modelService, tenantModelService, colosseumComputeService,
            tenant);
        this.virtualMachineRemoteModelService = modelService;
        this.monitorInstanceModelService = monitorInstanceModelService;
        this.rawMonitorModelService = rawMonitorModelService;
    }

    @Override protected void doWork(ModelService<VirtualMachine> modelService,
        ColosseumComputeService computeService) throws JobException {

        jpaApi().withTransaction(() -> {
            VirtualMachine virtualMachine = getT();
            computeService.deleteVirtualMachine(virtualMachine);
        });
    }

    @Override public boolean canStart() throws JobException {
        try {
            return jpaApi().withTransaction(() -> {
                VirtualMachine virtualMachine = getT();

                if (RemoteState.ERROR.equals(virtualMachine.getRemoteState())) {
                    throw new JobException(String
                        .format("Job %s can never start as virtualMachine %s is in error state.",
                            this, virtualMachine));
                }

                if (virtualMachine.instances().size() > 0) {
                    return false;
                }

                return RemoteState.OK.equals(virtualMachine.getRemoteState());

            });
        } catch (Throwable t) {
            throw new JobException(t);
        }
    }

    @Override public void onSuccess() throws JobException {
        // TODO when VM is deleted,
        // delete also MonitorIsntances,
        // if only one monitor instance, also delete monitor
        // update all monitors that are linked to it,
        // do this recursivley through ALL monitors
        // actually this should be handled by an event bus
        jpaApi().withTransaction(() -> {
            VirtualMachine t = getT();

            for(MonitorInstance mi : monitorInstanceModelService.getAll()){
                LOGGER.debug("Check to delete monitor instance " + mi.getId() + " for VM " + t.getId());

                boolean monitorInstanceAffected =
                        (mi.getVirtualMachine() != null && mi.getVirtualMachine().getId().equals(t.getId()));
                monitorInstanceAffected = monitorInstanceAffected ||
                        (t.publicIpAddress().isPresent() &&
                                (mi.getApiEndpoint() != null) &&
                                (mi.getApiEndpoint().equals(t.publicIpAddress().get().getIp())));

                if(monitorInstanceAffected){
                    // Delete monitor instance reference in raw monitor:
                    //TODO remove monitor completely and update
                    //TODO referencing monitors
                    //TODO recursive updating of changed monitors

                    for(RawMonitor rm : rawMonitorModelService.getAll()){
                        MonitorInstance miToRemove = null;

                        for(MonitorInstance mi2 : rm.getMonitorInstances()){
                            if(mi2.getId().equals(mi.getId())){
                                miToRemove = mi2;
                            }
                        }

                        if (rm.getMonitorInstances().size() <= 1) {
                            //TODO remove monitor completely and update
                            //TODO referencing monitors
                            //TODO recursive updating of changed monitors
                        }

                        if(miToRemove != null) {
                            rm.getMonitorInstances().remove(miToRemove);
                        }

                        rawMonitorModelService.save(rm);
                    }

                    //TODO Do the same for composed monitors
                    //TODO once cross-vm distribution of aggregation is integrated

                    monitorInstanceModelService.delete(mi);
                }
            }

            virtualMachineRemoteModelService.delete(t);
        });

        //jpaApi().withTransaction(() -> {
        //    VirtualMachine t = getT();
        //
        //    virtualMachineRemoteModelService.delete(t);
        //});
    }
}
