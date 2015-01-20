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

package models;

import models.generic.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 31.10.14.
 */
@Entity
public class CloudApi extends Model {

    /**
     * Empty constructor for hibernate.
     */
    public CloudApi() {
    }

    /**
     * Serial version.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The Api.
     */
    @ManyToOne(optional = false)
    private Api api;

    /**
     * The Cloud.
     */
    @ManyToOne(optional = false)
    private Cloud cloud;

    /**
     * The endpoint of the CloudApi
     */
    private String endpoint;

    public Api getApi() {
        return api;
    }

    public void setApi(Api api) {
        checkNotNull(api);
        this.api = api;
    }

    public Cloud getCloud() {
        return cloud;
    }

    public void setCloud(Cloud cloud) {
        checkNotNull(cloud);
        this.cloud = cloud;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        checkNotNull(endpoint);
        this.endpoint = endpoint;
    }


}
