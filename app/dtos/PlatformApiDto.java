package dtos;

import dtos.generic.ValidatableDto;
import dtos.validation.validators.NotNullOrEmptyValidator;

/**
 * Created by Daniel Seybold on 24.11.2016.
 */
public class PlatformApiDto extends ValidatableDto {


    private String internalProviderName;
    private String name;

    public PlatformApiDto(){
        super();
    }

    public PlatformApiDto( String name) {
        this.name = name;
    }

    public String getInternalProviderName() {
        return internalProviderName;
    }

    public void setInternalProviderName(String internalProviderName) {
        this.internalProviderName = internalProviderName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void validation() {
        validator(String.class).validate(this.name).withValidator(new NotNullOrEmptyValidator());
        validator(String.class).validate(this.internalProviderName)
                .withValidator(new NotNullOrEmptyValidator());
    }
}
