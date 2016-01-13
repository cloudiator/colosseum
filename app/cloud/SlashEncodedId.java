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

package cloud;


import de.uniulm.omi.cloudiator.sword.api.domain.Identifiable;
import models.Cloud;
import models.CloudCredential;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 06.07.15.
 */
public class SlashEncodedId {

    private static final String SEPARATOR = "/";

    private final String cloudCredential;
    private final String cloud;
    private final String id;

    private SlashEncodedId(String cloudCredential, String cloud, String id) {
        this.cloudCredential = cloudCredential;
        this.cloud = cloud;
        this.id = id;
    }

    public static SlashEncodedId of(CloudCredential cloudCredential, Cloud cloud,
        Identifiable identifiable) {
        return new SlashEncodedId(cloudCredential.getUuid(), cloud.getUuid(), identifiable.id());
    }

    public static SlashEncodedId of(String userId) {
        checkNotNull(userId, "userId must not be null.");
        checkArgument(!userId.isEmpty(), "userId must not be empty");
        final String[] parts = userId.split(SEPARATOR, 3);
        checkArgument(parts.length == 3,
            String.format("userId %s is not a valid user identifier.", userId));
        return new SlashEncodedId(parts[0], parts[1], parts[2]);
    }

    /**
     * Returns an id that uniquely defines this entity in a user scope.
     * <p>
     * Uses the id of the cloudCredential and the cloud to
     * create a unique identifier in a multi-cloud, multi-tenant
     * environment.
     *
     * @return a unique id.
     */
    public String userId() {
        return cloudCredential + SEPARATOR + cloud + SEPARATOR + id;
    }

    /**
     * Returns an id that identifies this entity in multi-cloud scope.
     * <p>
     * Uses the id of the cloud to create a unique identifier in multi-cloud
     * scope. Not necessarily unique in multi-tenant scope. However, two resources having the same
     * cloud id, are guaranteed the represent the same cloud resource, but possibly from the view of
     * two different users.
     *
     * @return a multi-cloud unique id.
     */
    public String cloudId() {
        return cloud + SEPARATOR + id;
    }

    /**
     * Returns an id that identifies this entity in single-cloud scope.
     * <p>
     * Returns the id of the cloud abstraction layer sword, uniquely identifying a resource in
     * single-cloud (and multi-region) scope. As two different clouds could use the same id, this is
     * not guaranteed to be unique in multi-cloud scope.
     *
     * @return a single-cloud unique id.
     */
    public String swordId() {
        return id;
    }

    @Override public String toString() {
        return cloudId();
    }
}
