package dtos;

import com.google.inject.Inject;
import com.google.inject.Provider;
import dtos.generic.ValidatableDto;
import dtos.validation.validators.ModelIdValidator;
import dtos.validation.validators.NotNullOrEmptyValidator;
import dtos.validation.validators.NotNullValidator;
import models.PlatformApi;
import models.service.BaseModelService;
import models.service.ModelService;

/**
 * Created by Daniel Seybold on 24.11.2016.
 */
public class PlatformDto extends ValidatableDto {

    private String name;
    private String endpoint;
    private Long platformApi;

    public PlatformDto(){
        super();
    }

    public PlatformDto(String name, String endpoint, Long platformApi) {
        this.name = name;
        this.endpoint = endpoint;
        this.platformApi = platformApi;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public Long getPlatformApi() {
        return platformApi;
    }

    public void setPlatformApi(Long platformApi) {
        this.platformApi = platformApi;
    }

    @Override
    public void validation() {
        validator(String.class).validate(this.name).withValidator(new NotNullOrEmptyValidator());
        validator(Long.class).validate(this.platformApi, "platformApi").withValidator(new NotNullValidator())
                .withValidator(new ModelIdValidator<>(PlatformDto.References.platformApiService.get()));
    }

    public static class References {

        @Inject
        private static Provider<BaseModelService<PlatformApi>> platformApiService;

        private References() {
        }
    }
}
