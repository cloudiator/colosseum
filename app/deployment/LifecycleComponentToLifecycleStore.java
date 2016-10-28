/*
 * Copyright (c) 2014-2016 University of Ulm
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

package deployment;

import com.google.common.collect.Maps;
import de.uniulm.omi.cloudiator.lance.container.spec.os.OperatingSystem;
import de.uniulm.omi.cloudiator.lance.lifecycle.LifecycleHandler;
import de.uniulm.omi.cloudiator.lance.lifecycle.LifecycleHandlerType;
import de.uniulm.omi.cloudiator.lance.lifecycle.LifecycleStore;
import de.uniulm.omi.cloudiator.lance.lifecycle.LifecycleStoreBuilder;
import de.uniulm.omi.cloudiator.lance.lifecycle.bash.BashBasedHandlerBuilder;
import de.uniulm.omi.cloudiator.lance.lifecycle.detector.DefaultDetectorFactories;
import models.LifecycleComponent;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by daniel on 11.10.16.
 */
public class LifecycleComponentToLifecycleStore
    implements Function<LifecycleComponent, LifecycleStore> {

    private final OsConverter osConverter;

    public LifecycleComponentToLifecycleStore() {
        osConverter = new OsConverter();
    }

    private Map<LifecycleHandlerType, String> buildCommandMap(LifecycleComponent lc) {
        Map<LifecycleHandlerType, String> commands = Maps.newHashMap();
        if (lc.getInit() != null) {
            commands.put(LifecycleHandlerType.INIT, lc.getInit());
        }
        if (lc.getPreInstall() != null) {
            commands.put(LifecycleHandlerType.PRE_INSTALL, lc.getPreInstall());
        }
        if (lc.getInstall() != null) {
            commands.put(LifecycleHandlerType.INSTALL, lc.getInstall());
        }
        if (lc.getPostInstall() != null) {
            commands.put(LifecycleHandlerType.POST_INSTALL, lc.getPostInstall());
        }
        if (lc.getPreStart() != null) {
            commands.put(LifecycleHandlerType.PRE_START, lc.getPreStart());
        }
        commands.put(LifecycleHandlerType.START, lc.getStart());
        if (lc.getPostStart() != null) {
            commands.put(LifecycleHandlerType.POST_START, lc.getPostStart());
        }
        if (lc.getPreStop() != null) {
            commands.put(LifecycleHandlerType.PRE_STOP, lc.getPreStop());
        }
        if (lc.getStop() != null) {
            commands.put(LifecycleHandlerType.STOP, lc.getStop());
        }
        if (lc.getPostStop() != null) {
            commands.put(LifecycleHandlerType.POST_STOP, lc.getPostStop());
        }
        return commands;
    }

    @Nullable @Override public LifecycleStore apply(LifecycleComponent lc) {

        Set<OperatingSystem> lanceOperatingSystems = lc.getApplicationComponents().stream().map(
            applicationComponent -> osConverter
                .apply(applicationComponent.getVirtualMachineTemplate().image().operatingSystem()))
            .collect(Collectors.toSet());

        final LifecycleStoreBuilder lifecycleStoreBuilder = new LifecycleStoreBuilder();

        for (OperatingSystem lanceOs : lanceOperatingSystems) {

            for (Map.Entry<LifecycleHandlerType, String> entry : buildCommandMap(lc).entrySet()) {
                final BashBasedHandlerBuilder bashBasedHandlerBuilder =
                    new BashBasedHandlerBuilder();
                bashBasedHandlerBuilder.setOperatingSystem(lanceOs);
                bashBasedHandlerBuilder.addCommand(entry.getValue());
                final LifecycleHandler lifecycleHandler =
                    bashBasedHandlerBuilder.build(entry.getKey());
                lifecycleStoreBuilder.setHandler(lifecycleHandler, entry.getKey());
            }

            if (lc.getStartDetection() != null) {
                final BashBasedHandlerBuilder startHandlerBuilder = new BashBasedHandlerBuilder();
                startHandlerBuilder.addCommand(lc.getStartDetection());
                startHandlerBuilder.setOperatingSystem(lanceOs);
                lifecycleStoreBuilder.setStartDetector(startHandlerBuilder.buildStartDetector());
            } else {
                lifecycleStoreBuilder
                    .setStartDetector(DefaultDetectorFactories.START_DETECTOR_FACTORY.getDefault());
            }
        }
        return lifecycleStoreBuilder.build();
    }
}
