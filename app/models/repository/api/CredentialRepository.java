/*
 * Copyright (c) 2015 University of Ulm
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

package models.repository.api;

import com.google.inject.ImplementedBy;
import models.Credential;
import models.repository.api.generic.ModelRepository;
import models.repository.impl.CredentialRepositoryJpa;

/**
 * Created by daniel seybold on 10.12.2014.
 */
@ImplementedBy(CredentialRepositoryJpa.class)
public interface CredentialRepository extends ModelRepository<Credential> {
}
