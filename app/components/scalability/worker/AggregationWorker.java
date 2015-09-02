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

package components.scalability.worker;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import components.execution.SimpleBlockingQueue;
import components.scalability.AggregationAccessService;
import components.scalability.internal.TsdbHelper;
import components.scalability.aggregation.Aggregation;
import de.uniulm.omi.cloudiator.axe.aggregator.communication.rmi.AggregatorServiceAccess;
import de.uniulm.omi.cloudiator.axe.aggregator.communication.rmi.ColosseumDetails;
import play.Logger;
import play.Play;

import java.rmi.RemoteException;

/**
 * Created by Frank on 30.07.2015.
 */
public class AggregationWorker implements Runnable {
    protected final static Logger.ALogger LOGGER = play.Logger.of("colosseum.scalability");

    private final SimpleBlockingQueue<Aggregation> aggregationQueue;

    @Inject public AggregationWorker(@Named("aggregationQueue") SimpleBlockingQueue<Aggregation> aggregationQueue) {
        this.aggregationQueue = aggregationQueue;
    }

    @Override public void run() {
        try {
            while(!Thread.currentThread().isInterrupted()){ /*TODO change with new version*/
                Aggregation job = aggregationQueue.take();
                Thread.sleep(2000); /* wait for transaction to be finished */

                //TODO has to be done on basis of the monitor instances instead of the monitors
                //TsdbHelper.getIpOfTSDB(job.getObject(), null /*TODO use this as filter if you only want to aggregate some */);
                //job.execute(AggregationAccessService.getService(), monitorInstances);3


                //TODO just for the moment only local service with hard-coded user details:
                final AggregatorServiceAccess localService = AggregationAccessService.getLocalService();

                if(localService == null){
                    LOGGER.error("Could not connect to RMI registry or object.");
                    continue;
                }

                ColosseumDetails credentials = new ColosseumDetails(
                    Play.application().configuration().getString("colosseum.scalability.aggregator.agent.local.protocol"),
                    Play.application().configuration().getString(
                        "colosseum.scalability.aggregator.agent.local.ip"),
                    Play.application().configuration().getInt(
                        "colosseum.scalability.aggregator.agent.local.port"),
                    Play.application().configuration().getString(
                        "colosseum.scalability.aggregator.agent.local.username"),
                    Play.application().configuration().getString(
                        "colosseum.scalability.aggregator.agent.local.tenant"),
                    Play.application().configuration().getString("colosseum.scalability.aggregator.agent.local.password"));
                try{
                    localService.setColosseum(credentials);
                } catch (RemoteException re){ // TODO outsource exception handling into separate client class
                    LOGGER.error("Could not set colosseum credentials to aggregator service.", re);
                }
                job.execute(localService);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
