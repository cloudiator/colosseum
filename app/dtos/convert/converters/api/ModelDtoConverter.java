package dtos.convert.converters.api;

import dtos.generic.api.Dto;
import dtos.generic.impl.Link;
import models.generic.Model;

import java.util.Set;

/**
 * @param <T>
 * @param <S>
 */
public interface ModelDtoConverter<T extends Model, S extends Dto> {

    public T toModel(S dto);

    public T toModel(S dto, T model);

    public S toDto(T model);

    public S toDto(T model, Set<Link> links);

}
