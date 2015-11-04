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
import de.uniulm.omi.cloudiator.sword.api.domain.Location;
import models.Cloud;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 06.07.15.
 */
public class DecoratedId {

    private final static String SEPARATOR = "/";

    private final String cloud;
    private final String id;


    private DecoratedId(String cloud, String id) {
        this.cloud = cloud;
        this.id = id;
    }

    static public DecoratedId of(Cloud cloud, Identifiable identifiable) {
        return new DecoratedId(cloud.getUuid(), identifiable.id());
    }

    static public DecoratedId of(Cloud cloud, Location location) {
        return new DecoratedId(cloud.getUuid(), location.id());
    }

    static public DecoratedId of(String colosseumId) {
        checkNotNull(colosseumId, "ColosseumId must not be null.");
        checkArgument(!colosseumId.isEmpty(), "ColosseumId must not be empty");
        final String[] parts = colosseumId.split(SEPARATOR, 2);
        checkArgument(parts.length == 2,
            String.format("ColosseumId does not contain separator %s", SEPARATOR));
        return new DecoratedId(parts[0], parts[1]);
    }

    public String colosseumId() {
        return cloud + SEPARATOR + id;
    }

    public String swordId() {
        return id;
    }

    @Override public String toString() {
        return colosseumId();
    }
}
