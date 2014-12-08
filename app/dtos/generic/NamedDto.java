package dtos.generic;

import play.data.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

/**
 * A Dto for named entities.
 */
public abstract class NamedDto implements ValidatableDto {

    public String name;

    /**
     * Default constructor, need for Play Forms API.
     */
    public NamedDto() {
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
    public List<ValidationError> validate() {
        List<ValidationError> errors = new ArrayList<>();
        if (name == null || name.length() == 0) {
            errors.add(new ValidationError("name", "No name was given."));
        }
        if (errors.isEmpty()) {
            return null;
        }
        return errors;
    }
}
