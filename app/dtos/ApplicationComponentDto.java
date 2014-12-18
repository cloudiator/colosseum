package dtos;

import com.google.inject.Inject;
import com.google.inject.Provider;
import dtos.generic.impl.AbstractValidatableDto;
import models.Application;
import models.Component;
import models.service.api.ApplicationServiceInterface;
import models.service.api.ComponentServiceInterface;
import play.data.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daniel seybold on 16.12.2014.
 */
public class ApplicationComponentDto extends AbstractValidatableDto {

    public static class References{

        @Inject
        public static Provider<ApplicationServiceInterface> applicationService;

        @Inject
        public static Provider<ComponentServiceInterface> componentService;
    }

    public Long application;

    public Long component;

    public ApplicationComponentDto(){

    }

    public ApplicationComponentDto(Long application, Long component){
        this.application = application;
        this.component = component;
    }

    @Override
    public List<ValidationError> validateNotNull() {
        List<ValidationError> errors = new ArrayList<>();

        //validate application reference
        Application application = null;
        if (this.application == null) {
            errors.add(new ValidationError("application", "The application is required."));
        } else {
            application = References.applicationService.get().getById(this.application);
            if (application == null) {
                errors.add(new ValidationError("application", "The given application is invalid."));
            }
        }

        //validate component reference
        Component component = null;
        if (this.component == null) {
            errors.add(new ValidationError("component", "The component is required."));
        } else {
            component = References.componentService.get().getById(this.component);
            if (component == null) {
                errors.add(new ValidationError("component", "The given component is invalid."));
            }
        }

        return errors;
    }
}
