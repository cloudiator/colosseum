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
import components.security.Password;
import models.FrontendUser;
import models.repository.api.FrontendUserRepository;
import models.service.impl.generic.BaseModelService;
import org.apache.commons.codec.binary.Base64;
import play.Logger;

/**
 * Created by daniel on 03.11.14.
 */
public class DefaultFrontendUserService extends BaseModelService<FrontendUser>
    implements models.service.api.FrontendUserService {

    @Inject public DefaultFrontendUserService(FrontendUserRepository frontendUserRepository) {
        super(frontendUserRepository);
    }

    @Override public FrontendUser getByMail(final String mail) {
        return ((FrontendUserRepository) this.modelRepository).findByMail(mail);
    }

    @Override public FrontendUser authenticate(String mail, String password) {
        Logger.info(String.format("Trying to authenticate %s using password ****", mail));
        FrontendUser fe = this.getByMail(mail);
        if (fe == null) {
            Logger.warn("Authentication failed, could not retrieve user from db");
            return null;
        }

        if (Password.getInstance().check(password.toCharArray(), fe.getPassword().toCharArray(),
            Base64.decodeBase64(fe.getSalt()))) {
            Logger.info("Authetication success. Authenticated as user with id=" + fe.getId());
            return fe;
        } else {
            Logger.warn("Authentication failed. Could not match password");
            return null;
        }
    }
}
