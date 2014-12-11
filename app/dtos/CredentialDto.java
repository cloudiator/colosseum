package dtos;

import dtos.generic.impl.AbstractValidatableDto;
import play.data.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

public class CredentialDto extends AbstractValidatableDto {

    public String user;
    public String secret;

    public CredentialDto() {

    }

    public CredentialDto(String user, String secret) {
        this.user = user;
        this.secret = secret;
    }

    @Override
    public List<ValidationError> validateNotNull() {
        List<ValidationError> errors = new ArrayList<>();

        if(this.user.isEmpty()){
            errors.add(new ValidationError("credentials", "User must not be empty"));
        }

        if(this.secret.isEmpty()){
            errors.add(new ValidationError("credentials", "Secret must not be empty"));
        }

        return errors;
    }
}
