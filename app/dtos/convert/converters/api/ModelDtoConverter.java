package dtos.convert.converters.api;

import dtos.generic.Dto;
import models.generic.Model;

/**
 *
 * @param <T>
 * @param <S>
 */
public interface ModelDtoConverter<T extends Model, S extends Dto> {

    public T toModel(S dto);

    public T toModel(S cloudDto, T cloud);

    public S toDto(T model);

}
