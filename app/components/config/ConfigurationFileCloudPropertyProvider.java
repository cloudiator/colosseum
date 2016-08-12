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

package components.config;

import com.typesafe.config.ConfigValue;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cloud.sword.CloudPropertyProvider;
import play.Configuration;
import play.Play;

/**
 * Created by daniel on 03.05.16.
 */
public class ConfigurationFileCloudPropertyProvider implements CloudPropertyProvider {

    @Override public Map<String, Object> properties() {

        final Configuration config =
            Play.application().configuration().getConfig("colosseum.cloud.properties");

        if (config == null) {
            return Collections.emptyMap();
        }

        final Set<Map.Entry<String, ConfigValue>> entries = config.entrySet();
        
        final Map<String, Object> properties = new HashMap<>(entries.size());

        for (Map.Entry<String, ConfigValue> entry : entries) {
            properties.put(entry.getKey(), entry.getValue().unwrapped());
        }

        return properties;
    }
}
