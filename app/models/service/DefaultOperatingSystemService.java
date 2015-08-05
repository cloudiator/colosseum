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

package models.service;

import com.google.inject.Inject;
import models.OperatingSystem;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by daniel on 23.07.15.
 */
public class DefaultOperatingSystemService extends BaseModelService<OperatingSystem>
    implements OperatingSystemService {

    @Inject public DefaultOperatingSystemService(ModelRepository<OperatingSystem> modelRepository) {
        super(modelRepository);
    }

    @Override public OperatingSystem findByImageName(String imageName) {
        Set<OperatingSystem> matches = new HashSet<>();
        for (OperatingSystem operatingSystem : getAll()) {
            String[] needsToContain = {operatingSystem.getVersion(),
                operatingSystem.getOperatingSystemArchitecture().toString(),
                operatingSystem.getOperatingSystemVendor().getName()};
            boolean isMatch = true;
            for (String check : needsToContain) {
                if (!imageName.toLowerCase().contains(check.toLowerCase())) {
                    isMatch = false;
                    break;
                }
            }
            if (isMatch) {
                matches.add(operatingSystem);
            }
        }
        if (matches.size() == 1) {
            return matches.iterator().next();
        }
        return null;
    }
}
