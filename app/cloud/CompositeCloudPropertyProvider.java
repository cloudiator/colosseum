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

package cloud;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import components.config.ConfigurationFileCloudPropertyProvider;
import models.Cloud;

/**
 * Created by daniel on 03.05.16.
 */
public class CompositeCloudPropertyProvider implements CloudPropertyProvider {

    public List<CloudPropertyProvider> cloudPropertyProviders;

    public CompositeCloudPropertyProvider(Cloud cloud) {
        cloudPropertyProviders = new ArrayList<>(2);
        this.cloudPropertyProviders.add(new ConfigurationFileCloudPropertyProvider());
        this.cloudPropertyProviders.add(cloud);
    }

    @Override public Map<String, Object> properties() {
        Map<String, Object> properties = new HashMap<>();
        for (CloudPropertyProvider cloudPropertyProvider : cloudPropertyProviders) {
            properties.putAll(cloudPropertyProvider.properties());
        }
        return properties;
    }
}
