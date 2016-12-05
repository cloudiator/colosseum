package dtos;

import dtos.generic.ValidatableDto;
import dtos.validation.validators.NotNullOrEmptyValidator;

/**
 * Created by Frank on 05.12.2016.
 */
public class PlatformComponentDto extends ValidatableDto {

    private String gitUrl;
    private String artifactPath;
    private String name;

    public PlatformComponentDto() {
        super();
    }

    public PlatformComponentDto(String gitUrl, String artifactPath, String name) {
        this.name = name;
        this.gitUrl = gitUrl;
        this.artifactPath = artifactPath;
    }

    public String getGitUrl() {
        return gitUrl;
    }

    public void setGitUrl(String gitUrl) {
        this.gitUrl = gitUrl;
    }

    public String getArtifactPath() {
        return artifactPath;
    }

    public void setArtifactPath(String artifactPath) {
        this.artifactPath = artifactPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void validation() {
        validator(String.class).validate(this.gitUrl).withValidator(new NotNullOrEmptyValidator()); //TODO mandatory?
        validator(String.class).validate(this.name).withValidator(new NotNullOrEmptyValidator());
        //validator(String.class).validate(this.artifactPath)
        //        .withValidator(new NotNullOrEmptyValidator());
    }
}