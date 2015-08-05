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
import models.service.api.ApiAccessTokenService;
import models.service.impl.generic.BaseModelService;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 19.12.14.
 */
public class DefaultApiAccessTokenService extends BaseModelService<ApiAccessToken>
    implements ApiAccessTokenService {

    @Inject public DefaultApiAccessTokenService(ApiAccessTokenRepository apiAccessTokenRepository) {
        super(apiAccessTokenRepository);
    }

    protected List<ApiAccessToken> getNonExpiredTokensForFrontendUser(FrontendUser frontendUser) {

        checkNotNull(frontendUser);

        return ((ApiAccessTokenRepository) this.modelRepository).findByFrontendUser(frontendUser)
            .stream()
            .filter(apiAccessToken -> apiAccessToken.getExpiresAt() > System.currentTimeMillis())
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override public boolean isValid(String token, FrontendUser frontendUser) {
        checkNotNull(token);
        checkNotNull(frontendUser);
        for (ApiAccessToken apiAccessToken : getNonExpiredTokensForFrontendUser(frontendUser)) {
            if (apiAccessToken.getToken().equals(token)) {
                return true;
            }
        }
        return false;
    }
}
