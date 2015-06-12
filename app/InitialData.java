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

import com.google.inject.Inject;
import models.FrontendGroup;
import models.FrontendUser;
import models.service.api.FrontendUserService;
import models.service.api.generic.ModelService;

/**
 * Created by daniel on 25.11.14.
 */
public class InitialData {

    private final FrontendUserService frontendUserService;
    private final ModelService<FrontendGroup> frontendGroupModelService;
    private static final String DEFAULT_GROUP = "admin";

    @Inject public InitialData(FrontendUserService frontendUserService,
        ModelService<FrontendGroup> frontendGroupModelService) {
        this.frontendUserService = frontendUserService;
        this.frontendGroupModelService = frontendGroupModelService;
    }

    public void loadInitialData() {
        /**
         * Creates a default system user and a default group.
         */
        if (frontendUserService.getAll().isEmpty()) {
            FrontendGroup frontendGroup = null;
            for (FrontendGroup storedFrontendGroup : frontendGroupModelService.getAll()) {
                if (DEFAULT_GROUP.equals(storedFrontendGroup.getName())) {
                    frontendGroup = storedFrontendGroup;
                    break;
                }
            }
            if (frontendGroup == null) {
                frontendGroup = new FrontendGroup(DEFAULT_GROUP);
                frontendGroupModelService.save(frontendGroup);
            }

            FrontendUser frontendUser =
                new FrontendUser("John", "Doe", "admin", "john.doe@example.com");
            frontendUserService.save(frontendUser);
            frontendGroup.getFrontendUsers().add(frontendUser);
            frontendGroupModelService.save(frontendGroup);
        }
    }
}
