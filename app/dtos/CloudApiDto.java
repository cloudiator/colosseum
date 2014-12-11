package dtos;

import com.google.inject.Inject;
import com.google.inject.Provider;
import dtos.generic.impl.AbstractValidatableDto;
import models.Api;
import models.Cloud;
import models.service.api.ApiServiceInterface;
import models.service.api.CloudServiceInterface;
import play.data.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daniel seybold on 11.12.2014.
 */
public class CloudApiDto extends AbstractValidatableDto {


    public static class References{

        @Inject
        public static Provider<ApiServiceInterface> apiService;

        @Inject
        public static Provider<CloudServiceInterface> cloudService;
    }

    public Long api;

    public Long cloud;

    public String endpoint;

    public CloudApiDto(){

    }

    public CloudApiDto(Long api, Long cloud, String endpoint){

        this.api = api;
        this.cloud = cloud;
        this.endpoint = endpoint;
    }

    @Override
    public List<ValidationError> validateNotNull() {
        List<ValidationError> errors = new ArrayList<>();

        //validate api reference
        Api api = null;
        if (this.api == null) {
            errors.add(new ValidationError("api", "The api is required."));
        } else {
            api = References.apiService.get().getById(this.api);
            if (api == null) {
                errors.add(new ValidationError("api", "The given api is invalid."));
            }
        }

        //validate cloud reference
        Cloud cloud = null;
        if (this.cloud == null) {
            errors.add(new ValidationError("cloud", "The cloud is required."));
        } else {
            cloud = References.cloudService.get().getById(this.cloud);
            if (cloud == null) {
                errors.add(new ValidationError("cloud", "The given cloud is invalid."));
            }
        }


        return errors;
    }
}
