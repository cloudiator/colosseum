package dtos;

import dtos.generic.ValidatableDto;
import dtos.validation.validators.ExpressionValidator;
import dtos.validation.validators.NotNullValidator;
import dtos.validation.validators.PredicateValidator;

/**
 * Created by Daniel Seybold on 28.11.2016.
 */
public class PlatformHardwareDto extends ValidatableDto {

    protected Integer numberOfCores;
    protected Long mbOfRam;
    protected Float localDiskSpace;

    public PlatformHardwareDto(Integer numberOfCores, Long mbOfRam, Float localDiskSpace) {
        this.numberOfCores = numberOfCores;
        this.mbOfRam = mbOfRam;
        this.localDiskSpace = localDiskSpace;
    }


    @Override
    public void validation() {
        validator(Integer.class).validate(numberOfCores).withValidator(new NotNullValidator())
                .withValidator(new ExpressionValidator(numberOfCores > 0));
        validator(Long.class).validate(mbOfRam).withValidator(new NotNullValidator())
                .withValidator(new ExpressionValidator(mbOfRam > 0));
        validator(Float.class).validate(localDiskSpace)
                .withValidator(new PredicateValidator<>(aFloat -> aFloat == null || aFloat > 0F));

    }

    public Integer getNumberOfCores() {
        return numberOfCores;
    }

    public void setNumberOfCores(Integer numberOfCores) {
        this.numberOfCores = numberOfCores;
    }

    public Long getMbOfRam() {
        return mbOfRam;
    }

    public void setMbOfRam(Long mbOfRam) {
        this.mbOfRam = mbOfRam;
    }

    public Float getLocalDiskSpace() {
        return localDiskSpace;
    }

    public void setLocalDiskSpace(Float localDiskSpace) {
        this.localDiskSpace = localDiskSpace;
    }
}
