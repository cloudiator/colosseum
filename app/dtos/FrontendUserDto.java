package dtos;

import dtos.generic.impl.ValidatableDto;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

public class FrontendUserDto extends ValidatableDto {

    public String firstName;
    public String lastName;
    public String mail;
    public String password;
    public String repeat;

    public FrontendUserDto() {

    }

    public FrontendUserDto(String firstName, String lastName, String mail, String password, String repeat) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
        this.password = password;
        this.repeat = repeat;
    }

    @Override
    public List<ValidationError> validateNotNull() {
        List<ValidationError> errors = new ArrayList<>();

        if(this.firstName.isEmpty()){
            errors.add(new ValidationError("frontenduser", "Firstname must not be empty"));
        }

        if(this.lastName.isEmpty()){
            errors.add(new ValidationError("frontenduser", "Lastname must not be empty"));
        }

        if(!Constraints.email().isValid(this.mail)){
            errors.add(new ValidationError("frontenduser", "Mail is not valid"));
        }

        if(this.password.isEmpty()){
            errors.add(new ValidationError("frontenduser", "Password must not be empty"));
        }

        if(this.repeat.isEmpty() || !repeat.equals(password)){
            errors.add(new ValidationError("frontenduser", "Passwords do not match"));
        }

        return errors;
    }
}
