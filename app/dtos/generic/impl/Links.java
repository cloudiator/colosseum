package dtos.generic.impl;

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
