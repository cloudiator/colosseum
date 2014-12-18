package dtos.generic.impl;

import play.data.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

/**
 * A Dto for named entities.
 */
public abstract class NamedDto extends AbstractValidatableDto {

    public String name;

    /**
     * Default constructor, need for Play Forms API.
     */
    public NamedDto() {
        super();
    }

    /**
     * Constructor setting the name.
     *
     * @param name the name of the object.
     */
    public NamedDto(String name) {
        this.name = name;
    }

    @Override
    public List<ValidationError> validateNotNull() {
        List<ValidationError> errors = new ArrayList<>();
        if (name == null || name.length() == 0) {
            errors.add(new ValidationError("name", "No name was given."));
        }
        return errors;
    }
}
