package dtos;

import dtos.generic.NamedDto;

import java.util.List;

public class LocationDto extends NamedDto {

    public List<Long> locationCodes;

    public Long parent;

    public String locationScope;

    public LocationDto() {

    }

    public LocationDto(String name, List<Long> locationCodes, Long parent, String locationScope) {
        super(name);
        this.locationCodes = locationCodes;
        this.parent = parent;
        this.locationScope = locationScope;
    }
}
