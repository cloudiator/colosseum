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

package models.service.impl;

import com.google.inject.Inject;
import models.ApiAccessToken;
import models.FrontendUser;
import models.repository.api.ApiAccessTokenRepository;
import models.service.api.ApiAccessTokenServiceInterface;
import models.service.impl.generic.ModelService;

import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 19.12.14.
 */
public class ApiAccessTokenService extends ModelService<ApiAccessToken> implements ApiAccessTokenServiceInterface {

    @Inject
    public ApiAccessTokenService(ApiAccessTokenRepository apiAccessTokenRepository) {
        super(apiAccessTokenRepository);
    }

    @Nullable
    protected ApiAccessToken getNewestNonExpiredTokenForFrontendUser(FrontendUser frontendUser) {
        ApiAccessToken newest = null;
        for (ApiAccessToken apiAccessToken : ((ApiAccessTokenRepository) this.modelRepository).findByFrontendUser(frontendUser)) {
            if (newest == null || apiAccessToken.getExpiresAt() > newest.getExpiresAt()) {
                newest = apiAccessToken;
            }
        }
        if (newest != null && newest.getExpiresAt() > System.currentTimeMillis()) {
            return newest;
        }
        return null;
    }

    @Override
    public boolean isValid(String token, FrontendUser frontendUser) {
        checkNotNull(token);
        checkNotNull(frontendUser);

        ApiAccessToken newestToken = this.getNewestNonExpiredTokenForFrontendUser(frontendUser);
        return newestToken != null && newestToken.getToken().equals(token);

    }
}
