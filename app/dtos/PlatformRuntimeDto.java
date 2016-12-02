package dtos;

import dtos.generic.ValidatableDto;
import dtos.validation.validators.NotNullValidator;
import dtos.validation.validators.PredicateValidator;

/**
 * Created by Daniel Seybold on 28.11.2016.
 */
public class PlatformRuntimeDto extends ValidatableDto {

    protected String language;
    protected String type;
    protected Float version;

    public PlatformRuntimeDto(String language, String type, Float version) {
        this.language = language;
        this.type = type;
        this.version = version;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Float getVersion() {
        return version;
    }

    public void setVersion(Float version) {
        this.version = version;
    }

    @Override
    public void validation() {
        validator(String.class).validate(language).withValidator(new NotNullValidator());
        validator(String.class).validate(type).withValidator(new NotNullValidator());
        validator(Float.class).validate(version)
                .withValidator(new PredicateValidator<>(aFloat -> aFloat == null || aFloat > 0F));

    }
}
