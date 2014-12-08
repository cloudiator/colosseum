package dtos.generic.api;

import play.data.validation.ValidationError;

import javax.annotation.Nullable;
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
    @Nullable
    public List<ValidationError> validate();

}
