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
import models.generic.RemoteModel;

/**
 * Created by daniel on 21.06.15.
 */
public class BaseRemoteModelService<T extends RemoteModel> extends BaseModelService<T>
    implements RemoteModelService<T> {

    protected final RemoteModelRepository<T> tRemoteModelRepository;

    @Inject public BaseRemoteModelService(RemoteModelRepository<T> tRemoteModelRepository) {
        super(tRemoteModelRepository);
        this.tRemoteModelRepository = tRemoteModelRepository;
    }

    @Override public T getByRemoteId(String remoteId) {
        return tRemoteModelRepository.findByRemoteId(remoteId);
    }
}
