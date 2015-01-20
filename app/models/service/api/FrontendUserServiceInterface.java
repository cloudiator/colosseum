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

package models.service.api;

import com.google.inject.ImplementedBy;
import models.FrontendUser;
import models.service.api.generic.ModelServiceInterface;
import models.service.impl.FrontendUserService;

/**
 * Created by daniel on 03.11.14.
 */
@ImplementedBy(FrontendUserService.class)
public interface FrontendUserServiceInterface extends ModelServiceInterface<FrontendUser>{

    public FrontendUser getByMail(String mail);

    /**
     * Tries to authenticate the user.
     *
     * @param mail
     *            the mail address given by the user
     * @param password
     *            the plain text given by the user.
     *
     * @return the user object if auth was successful, null if not.
     */
    public FrontendUser authenticate(String mail, String password);

}
