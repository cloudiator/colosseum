package dtos.generic.impl;

import dtos.generic.api.Dto;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by daniel on 18.12.14.
 */
public class DtoImpl implements Dto {

    private final Set<Link> links;

    public DtoImpl() {
        super();
        this.links = new HashSet<>();
    }

    @Override
    public Set<Link> getLinks() {
        return this.links;
    }
}
