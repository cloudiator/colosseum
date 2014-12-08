package dtos.generic;

import play.data.validation.ValidationError;

import java.util.List;

/**
 * Created by bwpc on 08.12.2014.
 */
public interface Validatable {

    /**
     * Provides a function to validate the object.
     * <p>
     * Used by the play framework forms.
     *
     * @return null if no errors are present, otherwise a list of validation errors.
     */
    public List<ValidationError> validate();

}
