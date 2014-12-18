package dtos.generic.impl;

import dtos.generic.api.ValidatableDto;
import play.data.validation.ValidationError;

import javax.annotation.Nullable;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 08.12.14.
 */
public abstract class AbstractValidatableDto extends DtoImpl implements ValidatableDto {

    public AbstractValidatableDto() {
        super();
    }

    @Nullable
    @Override
    public List<ValidationError> validate() {
        List<ValidationError> validationErrors = this.validateNotNull();
        checkNotNull(validateNotNull());
        if (validationErrors.isEmpty()) {
            return null;
        }
        return validationErrors;
    }

    /**
     * Validates the dto.
     * <p>
     * A more convenient method for validation of objects as it
     * does not expect to return null if no error occurs. Instead
     * an empty list is sufficient.
     *
     * @return empty list if the validation passed, a list
     * containing errors if it failed.
     */
    public abstract List<ValidationError> validateNotNull();
}
