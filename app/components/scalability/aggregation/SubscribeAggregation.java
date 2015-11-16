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

package components.scalability.aggregation;

import components.scalability.AggregatorEntitiesConverter;
import de.uniulm.omi.cloudiator.axe.aggregator.communication.rmi.AggregatorServiceAccess;
import de.uniulm.omi.cloudiator.axe.aggregator.communication.rmi.ColosseumDetails;
import de.uniulm.omi.cloudiator.axe.aggregator.communication.rmi.observer.ScalingObserverParameter;
import de.uniulm.omi.cloudiator.axe.aggregator.communication.rmi.observer.TelnetEventObserverParameter;
import de.uniulm.omi.cloudiator.axe.aggregator.communication.rmi.observer.TelnetMetricObserverParameter;
import models.Monitor;
import models.MonitorSubscription;
import models.scalability.SubscriptionType;
import play.Logger;
import play.Play;

import java.rmi.RemoteException;

/**
 * Created by Frank on 03.08.2015.
 */
public class SubscribeAggregation implements Aggregation<Monitor> {
    protected final MonitorSubscription subscription;
    protected final Monitor monitor;
     /*TODO dynamic port*/
     private static final int VISOR_TELNET_PORT =
         Play.application().configuration().getInt("colosseum.scalability.visor.telnet.port");
    private final static Logger.ALogger LOGGER = play.Logger.of("colosseum.scalability");

    public SubscribeAggregation(Monitor monitor, MonitorSubscription subscription) {
        super();
        this.subscription = subscription;
        this.monitor = monitor;
    }

    @Override public int getPriority() {
        return 0;
    }

    @Override public Monitor getObject() {
        return monitor;
    }

    @Override public void execute(AggregatorServiceAccess service) {
        try {
            if(this.subscription.getType() == SubscriptionType.CDO) {
                service.addObserver(
                    monitor.getId(),
                    new TelnetMetricObserverParameter(
                        subscription.getFilterValue(),
                        AggregatorEntitiesConverter.convert(subscription.getFilterType()),
                        subscription.getEndpoint(),
                        VISOR_TELNET_PORT,
                        subscription.getId().toString())
                );
            } else if(this.subscription.getType() == SubscriptionType.CDO_EVENT) {
                service.addObserver(
                    monitor.getId(),
                    new TelnetEventObserverParameter(
                        subscription.getFilterValue(),
                        AggregatorEntitiesConverter.convert(subscription.getFilterType()),
                        subscription.getEndpoint(),
                        VISOR_TELNET_PORT,
                        subscription.getId().toString())
                );
            } else if(this.subscription.getType() == SubscriptionType.SCALING) {
                ColosseumDetails cd =new ColosseumDetails(
                        Play.application().configuration().getString("colosseum.scalability.aggregator.scalingaction.protocol"),
                        Play.application().configuration().getString("colosseum.scalability.aggregator.scalingaction.ip"),
                        Play.application().configuration().getInt("colosseum.scalability.aggregator.scalingaction.port"),
                        Play.application().configuration().getString("colosseum.scalability.aggregator.scalingaction.username"),
                        Play.application().configuration().getString("colosseum.scalability.aggregator.scalingaction.tenant"),
                        Play.application().configuration().getString("colosseum.scalability.aggregator.scalingaction.password")
                );

                service.addObserver(
                    monitor.getId(),
                    new ScalingObserverParameter(
                        subscription.getFilterValue(),
                        AggregatorEntitiesConverter.convert(subscription.getFilterType()),
                        cd)
                );
            }
        } catch (RemoteException e) {
            LOGGER.error("Error when calling remote object to add observer. " + e);
        }
    }
}