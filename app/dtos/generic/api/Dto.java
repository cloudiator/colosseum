package dtos.generic.api;

import dtos.generic.impl.Link;

import java.util.Set;

/**
 * Interface for data transfer objects.
 */
public interface Dto {

    public Set<Link> getLinks();

}
