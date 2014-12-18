package dtos.convert.converters.impl;

import dtos.convert.converters.api.ModelDtoConverter;
import dtos.generic.api.Dto;
import dtos.generic.impl.Link;
import models.generic.Model;

import java.util.Set;

/**
 * Created by daniel on 18.12.14.
 */
public abstract class BaseConverter<T extends Model, S extends Dto> implements ModelDtoConverter<T, S> {

    public S toDto(T model, Set<Link> links) {
        S dto = this.toDto(model);
        dto.getLinks().addAll(links);
        return dto;
    }

}
