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

package dtos.generic;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 18.12.14.
 */
public class Links {

    private Links() {

    }

    public static Set<Link> fromSelfLink(String selfLink) {
        checkNotNull(selfLink);
        checkArgument(!selfLink.isEmpty());
        Set<Link> links = new HashSet<>(1);
        links.add(new Link(selfLink, Rel.SELF));
        return links;
    }

    public static Map<Long, Set<Link>> fromIdsAndSelfLinks(Map<Long, String> selfLinks) {
        checkNotNull(selfLinks);
        Map<Long, Set<Link>> map = new HashMap<>(selfLinks.size());

        for (Map.Entry<Long, String> entry : selfLinks.entrySet()) {
            map.put(entry.getKey(), fromSelfLink(entry.getValue()));
        }

        return map;
    }
}
