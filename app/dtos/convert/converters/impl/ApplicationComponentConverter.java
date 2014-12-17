package dtos.convert.converters.impl;

import com.google.inject.Inject;
import dtos.ApplicationComponentDto;
import dtos.convert.converters.api.ModelDtoConverter;
import models.Application;
import models.ApplicationComponent;
import models.Component;
import models.service.impl.ApplicationService;
import models.service.impl.ComponentService;
import scala.App;

import static com.google.inject.internal.util.$Preconditions.checkNotNull;
import static com.google.inject.internal.util.$Preconditions.checkState;

/**
 * Created by daniel seybold on 17.12.2014.
 */
public class ApplicationComponentConverter implements ModelDtoConverter<ApplicationComponent, ApplicationComponentDto> {

    private final ApplicationService applicationService;
    private final ComponentService componentService;

    @Inject
    ApplicationComponentConverter(ApplicationService applicationService, ComponentService componentService){

        checkNotNull(applicationService);
        checkNotNull(componentService);

        this.applicationService = applicationService;
        this.componentService = componentService;

    }

    /**
     * Sets the dto to the applicationComponent model.
     *
     * @param applicationComponent    the applicationComponent model where the dto should be set.
     * @param applicationComponentDto the dto to be set.
     * @return the merged applicationComponent object.
     */
    protected ApplicationComponent setDto(ApplicationComponent applicationComponent, ApplicationComponentDto applicationComponentDto){

        Application application = applicationService.getById(applicationComponentDto.application);
        checkState(application != null,"Could not retrieve application for id: " + applicationComponentDto.application);
        applicationComponent.setApplication(application);

        Component component = componentService.getById(applicationComponentDto.component);
        checkState(component != null, "Could not retrieve component for id: " + applicationComponentDto.component);
        applicationComponent.setComponent(component);

        return applicationComponent;
    }


    @Override
    public ApplicationComponent toModel(ApplicationComponentDto dto) {
        checkNotNull(dto);
        return setDto(new ApplicationComponent(), dto);
    }

    @Override
    public ApplicationComponent toModel(ApplicationComponentDto dto, ApplicationComponent model) {
        checkNotNull(dto);
        checkNotNull(model);
        return setDto(model, dto);
    }

    @Override
    public ApplicationComponentDto toDto(ApplicationComponent model) {
        checkNotNull(model);
        return new ApplicationComponentDto(model.getApplication().getId(), model.getComponent().getId());
    }
}
